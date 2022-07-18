package com.example.course.service;

import com.example.course.config.MailComponents;
import com.example.course.domain.Member;
import com.example.course.exception.MemberException;
import com.example.course.model.member.MemberDto;
import com.example.course.repository.MemberRepository;
import com.example.course.type.ErrorCode;
import com.example.course.type.SearchType;
import com.example.course.type.UserRole;
import com.example.course.type.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    private Member getMember(String userId) {
        return memberRepository.findById(userId).orElseThrow(() ->
                new MemberException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public void updateLastLoginDate(String userid,LocalDateTime now) {
        Member member = getMember(userid);
        member.changeLastLoginDate(now);
        memberRepository.save(member);
    }

    @Override
    public MemberDto updateStatus(String userId, UserStatus userStatus) {
        Member member = memberRepository.findById(userId).orElseThrow(() ->
                new MemberException(ErrorCode.USER_NOT_FOUND));
        member.updateStatus(userStatus);
        return MemberDto.fromEntity(memberRepository.save(member));
    }

    @Override
    public MemberDto detail(String userId) {
        Member member = getMember(userId);
        return MemberDto.fromEntity(member);
    }



    @Override
    public Page<MemberDto> selectAll(String searchType, String searchValue, Pageable pageable) {
        Page<Member> members;
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(
                page, pageable.getPageSize(),
                Sort.Direction.DESC,
                "regDate");

        if (searchType.equals(SearchType.USER_ID.getValue())) {
            members = memberRepository.findByUseridContaining(searchValue, pageable);
        } else if (searchType.equals(SearchType.USER_NAME.getValue())) {
            members = memberRepository.findByUserNameContaining(searchValue, pageable);
        } else if (searchType.equals(SearchType.PHONE.getValue())) {
            members = memberRepository.findByPhoneContaining(searchValue, pageable);
        } else {
            members = memberRepository.findAll(pageable);
        }
        return members.map(MemberDto::fromEntity);
    }


    @Transactional
    @Override
    public boolean insert(String userId, String userName, String password, String phone) {
        if (memberRepository.existsById(userId)) {
            return false;
        }
        String authKey = UUID.randomUUID().toString();
        String encodingPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Member saveMember = memberRepository.save(Member.builder()
                .userid(userId)
                .userName(userName)
                .password(encodingPassword)
                .phone(phone)
                .emailAuth(false)
                .emailAuthKey(authKey)
                .regDate(LocalDateTime.now())
                .userRole(UserRole.ROLE_SEMI_USER)
                .userStatus(UserStatus.WAITING_JOIN)
                .build());

        mailComponents.sendMail(saveMember.getUserid(),
                "가입을 축하드립니다.",
                "<p>회원가입을 완료하시려면 아래 링크를 클릭해 주세요.</p>" +
                        "<div>" +
                        "<a href='http://localhost:8080/member/email-auth?emailAuthKey" +
                        "=" + authKey + "'>링크</a>" +
                        "</div>");

        return true;
    }

    @Override
    public void emailAuth(String emailAuthKey) {
        Member member = memberRepository.findByEmailAuthKey(emailAuthKey).orElseThrow(() ->
                new MemberException(ErrorCode.AUTH_KEY_NOT_FOUND));
        if (member.isEmailAuth()) {
            return;
        }
        member.userJoinSuccess();
        memberRepository.save(member);
    }

    @Override
    public boolean sendResetPassword(String userId, String userName) {
        Member member =
                memberRepository.findByUseridAndUserName(userId, userName).orElseThrow(() ->
                        new MemberException(ErrorCode.USER_NOT_FOUND));

        String resetPasswordKey = UUID.randomUUID().toString();
        member.setPasswordKeyAndLimitDay(resetPasswordKey);
        memberRepository.save(member);
        mailComponents.sendMail(member.getUserid(),
                "비밀번호 초기화 메일",
                "<p>비밀번호를 초기화 하기 위해 아래 링크를 클릭해 주세요.</p>" +
                        "<div>" +
                        "<a href='http://localhost:8080/member/reset/password" +
                        "?resetPasswordKey=" + resetPasswordKey + "'>비밀번호 초기화 링크</a>" +
                        "</div>");
        return true;
    }

    @Override
    public boolean resetPassword(String password, String resetPasswordKey) {
        Member member = memberRepository.findByResetPasswordKey(resetPasswordKey).orElseThrow(() ->
                new MemberException(ErrorCode.AUTH_KEY_NOT_FOUND));

        if (member.getResetPasswordLimitDate().isBefore(LocalDateTime.now())) {
            throw new MemberException(ErrorCode.EXPIRED);
        }

        String encodingPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.changePassword(encodingPassword);
        memberRepository.save(member);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member =
                memberRepository.findById(username).orElseThrow(() ->
                        new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getErrorMessage()));

        if (!member.isEmailAuth()) {
            throw new MemberException(ErrorCode.NOT_AUTH);
        }

        if (member.getUserStatus() == UserStatus.CAN_NOT_USE) {
            throw new MemberException(ErrorCode.SUSPENDED_MEMBER);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.ROLE_USER.toString()));

        if (member.getUserRole() == UserRole.ROLE_ADMIN) {
            grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.toString()));
        }

        return new User(member.getUserid(), member.getPassword(), grantedAuthorities);
    }
}