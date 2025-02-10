package com.example.scheduleserver.dto.schedule;

import com.example.scheduleserver.validate.AddSchedule;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotBlank(groups = {AddSchedule.class}, message = "'title' Must not be Empty.")
    private final String title;

    @NotBlank(groups = {AddSchedule.class}, message = "'contents' Must not be Empty.")
    private final String contents;

    public ScheduleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
