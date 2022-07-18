package com.example.course.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USED_USER_ID("이미 사용중인 아이디입니다."),
    USER_NOT_FOUND("회원이 존재하지 않습니다."),
    CATEGORY_NOT_FOUND("해당 카테고리가 존재하지 않습니다."),
    FAILED_LOGIN("로그인에 실패했습니다."),
    SUSPENDED_MEMBER("이용이 정지된 회원입니다."),
    NOT_AUTH("이메일 인증이 필요합니다."),
    EXPIRED("기한이 만료됐습니다."),
    USED_CATEGORY_NAME("이미 사용중인 카테고리명입니다."),
    USED_COURSE_SUBJECT("이미 존재하는 강좌입니다."),
    COURSE_NOT_FOUND("존재하지 않는 강의입니다."),
    BANNER_NOT_FOUND("존재하지 않는 배너입니다."),
    HISTORY_NOT_FOUND("로그인 기록이 없습니다."),
    AUTH_KEY_NOT_FOUND("인증키를 찾을 수 없습니다."),
    ;

    private String errorMessage;

}
