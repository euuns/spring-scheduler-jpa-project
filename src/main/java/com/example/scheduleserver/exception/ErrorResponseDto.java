package com.example.scheduleserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {

    private HttpStatus httpStatus;
    private String message;

    public ErrorResponseDto(ExceptionCode exceptionCode) {
        this.httpStatus = exceptionCode.getStatus();
        this.message = exceptionCode.getMessage();
    }
}
