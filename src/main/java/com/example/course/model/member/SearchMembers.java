package com.example.course.model.member;

import lombok.*;

public class SearchMembers {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    @Setter
    public static class Request {

        private String searchType = "all";
        private String searchValue;

    }

}
