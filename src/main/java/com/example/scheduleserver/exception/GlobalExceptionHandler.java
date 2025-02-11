package com.example.scheduleserver.exception;

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
    public ResponseEntity<List<FiledErrorResponseDto>> violationException(MethodArgumentNotValidException e) {
        List<FiledErrorResponseDto> errors = new ArrayList<>();

        // 생성된 필드 에러를 모두 가져옴
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        // 에러 정보를 반환하기 위해 ErrorResponseDto에 발생 필드와 메세지 저장
        for (FieldError err : fieldErrors) {
            errors.add(new FiledErrorResponseDto(err.getField(), err.getDefaultMessage()));
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }


    // Enum을 사용해 예외 내역을 Dto에 담아서 반환하도록 지정
    @ExceptionHandler(ValidException.class)
    public ResponseEntity<ErrorResponseDto> sessionNotEqualException(ValidException e) {
        return new ResponseEntity<>(new ErrorResponseDto(e.getExceptionCode()), e.getExceptionCode().getStatus());
    }

}
