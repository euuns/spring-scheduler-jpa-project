package com.example.scheduleserver.exception;

public class SessionUserNotEqualsException extends RuntimeException {

    // 로그인한 유저와 요청 user_id가 맞지 않을 경우 예외 처리
    public SessionUserNotEqualsException() {
        super("Invalid Access. Please check again.");
    }

}
