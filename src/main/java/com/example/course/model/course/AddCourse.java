package com.example.course.model.course;

import lombok.*;

import java.time.LocalDate;

public class AddCourse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @ToString
    @Setter
    public static class Request {

        private Long id;
        private Long categoryId;
        private String keyword;
        private String imagePath;
        private String subject;
        private String summary;
        private String contents;
        private Long price;
        private Long salePrice;
        private String saleEndDate;

    }

}
