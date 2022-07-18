package com.example.course.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {

    USER_ID("userId"),
    USER_NAME("userName"),
    PHONE("phone");

    private String value;

}
