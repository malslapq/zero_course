package com.example.course.exception;

import com.example.course.type.ErrorCode;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MemberException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public MemberException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getErrorMessage();

    }

}

