package com.example.scheduleserver.exception;

import com.example.scheduleserver.config.MessageUtil;
import org.springframework.http.HttpStatus;

public enum ExceptionCode {

    // 요청 id가 잘못된 경우
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER_NOT_FOUND"),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE_NOT_FOUND"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND"),

    // 요청한 인물이 유효성 검사를 통과하지 못했을 경우
    SESSION_NOT_VALID(HttpStatus.FORBIDDEN, "SESSION_NOT_VALID"),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "EMAIL_NOT_FOUND"),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "PASSWORD_NOT_MATCH"),

    // 요청 페이지가 잘못된 경우
    PAGE_NOT_POSITIVE(HttpStatus.NOT_FOUND,"PAGE_NOT_POSITIVE"),
    PAGE_OVER(HttpStatus.NOT_FOUND,"PAGE_OVER");


    private final HttpStatus status;
    private final String message;


    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public String getMessage(){
        return MessageUtil.getMessage(message);
    }
}
