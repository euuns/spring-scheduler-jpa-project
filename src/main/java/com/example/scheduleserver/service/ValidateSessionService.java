package com.example.scheduleserver.service;

import com.example.scheduleserver.exception.ExceptionCode;
import com.example.scheduleserver.exception.ValidException;

public abstract class ValidateSessionService {

    protected void validateSessionUser(Long sessionUserId, Long scheduleUserId) {
        if (!sessionUserId.equals(scheduleUserId)) {
            throw new ValidException(ExceptionCode.SESSION_NOT_VALID);
        }
    }
}
