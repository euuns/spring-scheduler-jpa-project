package com.example.scheduleserver.dto;

import lombok.Getter;

@Getter
public class ErrorResponseDto {

    private String filed;
    private String message;


    public ErrorResponseDto(String filed, String message) {
        this.filed = filed;
        this.message = message;
    }

}
