package com.example.scheduleserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @Size(max = 200, message = "You cannot enter more than 100 characters.")
    @NotBlank(message = "Please enter the details.")
    private final String contents;

    public CommentRequestDto(String contents) {
        this.contents = contents;
    }
}
