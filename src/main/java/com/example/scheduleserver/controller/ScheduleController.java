package com.example.scheduleserver.controller;

import com.example.scheduleserver.dto.schedule.ScheduleRequestDto;
import com.example.scheduleserver.dto.schedule.ScheduleResponseDto;
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
    @PostMapping("/writing")
    public ResponseEntity<ScheduleResponseDto> addSchedule(@Validated(AddSchedule.class) @RequestBody ScheduleRequestDto requestDto, HttpServletRequest httpServletRequest) {
        ScheduleResponseDto schedule = scheduleService.addSchedule(requestDto.getTitle(), requestDto.getContents(), httpServletRequest.getSession());
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getScheduleList(@RequestParam(required = false, defaultValue = "1", value = "page") int pageNo) {
        // Page는 0부터 시작. 사용자는 1부터 입력. -> 인자에 '-1'을 넣어 1페이지를 요청했을 때 0 Page가 나오도록 지정
        List<ScheduleResponseDto> scheduleList = scheduleService.getScheduleList(pageNo-1);
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
        ScheduleResponseDto schedule = scheduleService.updateSchedule(id, requestDto.getTitle(), requestDto.getContents(), httpServletRequest.getSession());
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }


    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        scheduleService.deleteSchedule(id, httpServletRequest.getSession());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
