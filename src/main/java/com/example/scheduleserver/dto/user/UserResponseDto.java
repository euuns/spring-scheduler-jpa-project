package com.example.scheduleserver.dto.user;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String name;
    private final String email;
    private final String createdDate;
    private final String modifiedDate;

    public UserResponseDto(String name, String email, String createdDate, String modifiedDate) {
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
