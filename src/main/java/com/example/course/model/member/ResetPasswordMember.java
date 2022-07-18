package com.example.course.model.member;

import lombok.*;

public class ResetPasswordMember {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    @Setter
    public static class Request {

        private String password;
        private String resetPasswordKey;

    }

}
