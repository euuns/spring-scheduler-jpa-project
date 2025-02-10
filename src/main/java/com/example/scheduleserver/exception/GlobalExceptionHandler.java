package com.example.scheduleserver.exception;

import com.example.scheduleserver.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {


    // Filed Error 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDto>> violationException(MethodArgumentNotValidException e) {
        List<ErrorResponseDto> errors = new ArrayList<>();

        // 생성된 필드 에러를 모두 가져옴
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        // 에러 정보를 반환하기 위해 ErrorResponseDto에 발생 필드와 메세지 저장
        for (FieldError err : fieldErrors) {
            errors.add(new ErrorResponseDto(err.getField(), err.getDefaultMessage()));
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }


    // Request와 login한 유저(Session을 받은 유저) 일치하지 않는 예외 처리
    @ExceptionHandler(SessionUserNotEqualsException.class)
    public ResponseEntity<String> sessionNotEqualException(SessionUserNotEqualsException e) {
        // BAD_REQUEST와 함께 에러 메세지 출력
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
