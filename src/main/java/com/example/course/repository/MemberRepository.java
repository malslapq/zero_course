package com.example.course.repository;

import com.example.course.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {


    Optional<Member> findByResetPasswordKey(String resetPasswordKey);
    Optional<Member> findByEmailAuthKey(String emailAuthKey);
    Optional<Member> findByUseridAndUserName(String userId, String userName);

    Page<Member> findByUseridContaining(String userId, Pageable pageable);
    Page<Member> findByUserNameContaining(String userName, Pageable pageable);
    Page<Member> findByPhoneContaining(String phone, Pageable pageable);

}
