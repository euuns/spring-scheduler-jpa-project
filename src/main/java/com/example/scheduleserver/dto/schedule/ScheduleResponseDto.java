package com.example.scheduleserver.dto.schedule;

import com.example.scheduleserver.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private String name;
    private String title;
    private final String contents;
    private final String createdDate;
    private final String modifiedDate;

    public ScheduleResponseDto(String name, String title, String contents, String createdDate, String modifiedDate) {
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static ScheduleResponseDto toDto(Schedule schedule) {
        return new ScheduleResponseDto(schedule.getUser().getName(), schedule.getTitle(), schedule.getContents(),
                schedule.getCreatedDate(), schedule.getModifiedDate());
    }
}
