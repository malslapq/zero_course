package com.example.course.domain;

import com.example.course.type.UserRole;
import com.example.course.type.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Member {

    @Id
    private String userid;
    private String userName;
    private String password;
    private String phone;
    @CreatedDate
    private LocalDateTime regDate;
    private boolean emailAuth;
    private String emailAuthKey;
    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDate;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private LocalDateTime lastLoginDate;

    public void changeLastLoginDate(LocalDateTime now) {
        this.lastLoginDate = now;
    }

    public void updateStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void userJoinSuccess() {
        this.userRole = UserRole.ROLE_USER;
        this.userStatus = UserStatus.USING;
        this.emailAuthKey = null;
        this.emailAuth = true;
    }

    public void setPasswordKeyAndLimitDay(String key) {
        this.resetPasswordKey = key;
        this.resetPasswordLimitDate = LocalDateTime.now().plusMinutes(30);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

}
