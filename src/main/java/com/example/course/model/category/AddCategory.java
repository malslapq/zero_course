package com.example.course.model.category;

import lombok.*;

public class AddCategory {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    @Setter
    public static class Request {

        private String categoryName;

    }

}
