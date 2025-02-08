package com.example.scheduleserver.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private final Long id;
    private final String title;
    private final String contents;

    public ScheduleRequestDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }
}
