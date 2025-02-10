package com.example.scheduleserver.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final String userName;
    private final String scheduleTitle;
    private final String contents;
    private final String createdDate;
    private final String modifiedDate;

    public CommentResponseDto(String userName, String scheduleTitle, String contents, String createdDate, String modifiedDate) {
        this.userName = userName;
        this.scheduleTitle = scheduleTitle;
        this.contents = contents;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
