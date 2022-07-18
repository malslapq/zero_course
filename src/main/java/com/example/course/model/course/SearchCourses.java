package com.example.course.model.course;

import lombok.*;

public class SearchCourses {

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
