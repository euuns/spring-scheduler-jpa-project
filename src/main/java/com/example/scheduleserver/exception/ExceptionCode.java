package com.example.scheduleserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    // 요청 id가 잘못된 경우
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 id를 찾을 수 없습니다.", "The ID cannot be found."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당되는 id의 글을 찾을 수 없습니다.","The SCHEDULE ID cannot be found."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당되는 id의 댓글을 찾을 수 없습니다.","The COMMENT ID cannot be found."),

    // 요청한 인물이 유효성 검사를 통과하지 못했을 경우
    SESSION_NOT_VALID(HttpStatus.FORBIDDEN, "잘못된 접근입니다. 다시 확인해주세요.", "Invalid Access. Please check again."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 email 입니다.", "Does not exist EMAIL"),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.", "PASSWORD does not match."),

    // 요청 페이지가 잘못된 경우
    PAGE_NOT_POSITIVE(HttpStatus.NOT_FOUND,"존재하지 않는 페이지 입니다. (0 이하는 올 수 없습니다.) ","This page doesn't exist. (Value never below Zero)"),
    PAGE_OVER(HttpStatus.NOT_FOUND,"존재하지 않는 페이지 입니다.","This page doesn't exist.");


    private final HttpStatus status;
    private final String messageKor;
    private final String messageEng;

    ExceptionCode(HttpStatus status, String messageKor, String messageEng) {
        this.status = status;
        this.messageKor = messageKor;
        this.messageEng = messageEng;
    }
}
