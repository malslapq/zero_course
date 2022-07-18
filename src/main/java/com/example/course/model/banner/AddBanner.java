package com.example.course.model.banner;

import lombok.*;

public class AddBanner {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request {

        private Long id;
        private String name;
        private int sortValue;
        private boolean status;

    }

}
