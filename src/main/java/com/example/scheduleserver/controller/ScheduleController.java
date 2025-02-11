package com.example.scheduleserver.controller;

import com.example.scheduleserver.dto.schedule.ScheduleRequestDto;
import com.example.scheduleserver.dto.schedule.ScheduleResponseDto;
import com.example.scheduleserver.entity.User;
import com.example.scheduleserver.service.ScheduleService;
import com.example.scheduleserver.validate.AddSchedule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Validated
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping("")
    public ResponseEntity<ScheduleResponseDto> addSchedule(@Validated(AddSchedule.class) @RequestBody ScheduleRequestDto requestDto, HttpServletRequest httpServletRequest) {
        User login = (User) httpServletRequest.getSession().getAttribute("login");
        ScheduleResponseDto schedule = scheduleService.addSchedule(requestDto.getTitle(), requestDto.getContents(), login);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getScheduleList(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) {
        List<ScheduleResponseDto> scheduleList = scheduleService.getScheduleList(pageNo);
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }


    // 선택 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findByIdSchedule(@PathVariable Long id) {
        ScheduleResponseDto schedule = scheduleService.findByIdSchedule(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }


    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto, HttpServletRequest httpServletRequest) {
        User login = (User) httpServletRequest.getSession().getAttribute("login");
        ScheduleResponseDto schedule = scheduleService.updateSchedule(id, requestDto.getTitle(), requestDto.getContents(), login);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }


    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        User login = (User) httpServletRequest.getSession().getAttribute("login");
        scheduleService.deleteSchedule(id, login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
