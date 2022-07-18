package com.example.course.model.member;

import com.example.course.domain.Member;
import com.example.course.type.UserRole;
import com.example.course.type.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private String userId;
    private String userName;
    private String phone;
    private LocalDateTime regDate;
    private boolean emailAuth;
    private UserRole userRole;
    private UserStatus userStatus;
    private LocalDateTime lastLoginDate;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .userId(member.getUserid())
                .userName(member.getUserName())
                .phone(member.getPhone())
                .regDate(member.getRegDate())
                .emailAuth(member.isEmailAuth())
                .userRole(member.getUserRole())
                .userStatus(member.getUserStatus())
                .lastLoginDate(member.getLastLoginDate())
                .build();
    }

}
