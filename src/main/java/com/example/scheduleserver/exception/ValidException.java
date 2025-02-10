package com.example.scheduleserver.exception;

import lombok.Getter;

@Getter
public class ValidException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    // 로그인한 유저와 요청 user_id가 맞지 않을 경우 예외 처리
    public ValidException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

}
