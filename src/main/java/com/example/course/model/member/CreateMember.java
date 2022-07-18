package com.example.course.model.member;

import lombok.*;

public class CreateMember {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    @Setter
    public static class Request {

        private String userId;
        private String userName;
        private String password;
        private String phone;

    }


    public static class Response {

    }

}
