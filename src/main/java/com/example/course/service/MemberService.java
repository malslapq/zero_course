package com.example.course.service;

import com.example.course.model.member.MemberDto;
import com.example.course.type.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;

public interface MemberService extends UserDetailsService {

    void updateLastLoginDate(String userid, LocalDateTime now);

    boolean insert(String userId, String userName, String password, String phone);

    void emailAuth(String emailAuthKey);

    boolean sendResetPassword(String userId, String userName);

    boolean resetPassword(String password, String resetPasswordKey);

    Page<MemberDto> selectAll(String searchType, String searchValue, Pageable pageable);

    MemberDto detail(String userId);

    MemberDto updateStatus(String userId, UserStatus userStatus);
}
