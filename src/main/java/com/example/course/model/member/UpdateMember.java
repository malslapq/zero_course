package com.example.course.model.member;

import lombok.*;

public class UpdateMember {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    @Setter
    public static class Request {

        private String userId;
        private String userName;

    }



}
