package com.example.scheduleserver.exception;

import lombok.Getter;

@Getter
public class ValidException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public ValidException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

}
