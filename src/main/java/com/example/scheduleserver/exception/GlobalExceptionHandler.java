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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDto>> violationException(MethodArgumentNotValidException e) {
        List<ErrorResponseDto> errors = new ArrayList<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        for (FieldError err:fieldErrors) {
            errors.add(new ErrorResponseDto(err.getField(), err.getDefaultMessage()));
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

}
