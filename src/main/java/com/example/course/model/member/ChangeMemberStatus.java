package com.example.course.model.member;

import com.example.course.type.UserStatus;
import lombok.*;

public class ChangeMemberStatus {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    @Setter
    public static class Request {

        private String userId;
        private UserStatus userStatus;

    }

}
