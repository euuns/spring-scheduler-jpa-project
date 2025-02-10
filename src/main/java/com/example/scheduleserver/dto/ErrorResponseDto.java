package com.example.scheduleserver.dto;

import com.example.scheduleserver.exception.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {

    private HttpStatus httpStatus;
    private String messageKor;
    private String messageEng;

    public ErrorResponseDto(ExceptionCode exceptionCode) {
        this.httpStatus = exceptionCode.getStatus();
        this.messageKor = exceptionCode.getMessageKor();
        this.messageEng = exceptionCode.getMessageEng();
    }
}
