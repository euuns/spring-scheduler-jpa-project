package com.example.scheduleserver.controller;

import com.example.scheduleserver.dto.ScheduleRequestDto;
import com.example.scheduleserver.dto.ScheduleResponseDto;
import com.example.scheduleserver.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping("/writing")
    public ResponseEntity<ScheduleResponseDto> addSchedule(@RequestBody ScheduleRequestDto requestDto, HttpServletRequest httpServletRequest){
        ScheduleResponseDto schedule = scheduleService.addSchedule(requestDto.getTitle(), requestDto.getContents(), httpServletRequest.getSession());
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getScheduleList(){
        List<ScheduleResponseDto> scheduleList = scheduleService.getScheduleList();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }


    // 선택 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findByIdSchedule(@PathVariable Long id){
        ScheduleResponseDto schedule = scheduleService.findByIdSchedule(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }


    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto, HttpServletRequest httpServletRequest){
        ScheduleResponseDto schedule = scheduleService.updateSchedule(id, requestDto.getTitle(), requestDto.getContents(), httpServletRequest.getSession());
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }


    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpServletRequest httpServletRequest){
        scheduleService.deleteSchedule(id, httpServletRequest.getSession());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
