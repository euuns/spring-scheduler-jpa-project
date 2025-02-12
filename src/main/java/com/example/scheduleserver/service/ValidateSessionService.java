package com.example.scheduleserver.service;

import com.example.scheduleserver.exception.ExceptionCode;
import com.example.scheduleserver.exception.ValidException;
import org.springframework.stereotype.Service;

@Service
public class ValidateSessionService {

    public void validateSessionUser(Long sessionUserId, Long requestUserId) {
        if (!sessionUserId.equals(requestUserId)) {
            throw new ValidException(ExceptionCode.SESSION_NOT_VALID);
        }
    }
}
