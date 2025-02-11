package com.example.scheduleserver.exception;

import lombok.Getter;

@Getter
public class FiledErrorResponseDto {

    private String filed;
    private String message;


    public FiledErrorResponseDto(String filed, String message) {
        this.filed = filed;
        this.message = message;
    }

}
