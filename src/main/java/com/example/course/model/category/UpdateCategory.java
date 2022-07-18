package com.example.course.model.category;

import com.example.course.type.CategoryStatus;
import lombok.*;

public class UpdateCategory {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    @Setter
    public static class Request {

        private Long id;
        private String categoryName;
        private int sortValue;
        private CategoryStatus categoryStatus;

    }



}
