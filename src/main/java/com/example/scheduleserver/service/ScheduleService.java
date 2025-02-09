package com.example.scheduleserver.service;

import com.example.scheduleserver.dto.ScheduleResponseDto;
import com.example.scheduleserver.entity.Schedule;
import com.example.scheduleserver.entity.User;
import com.example.scheduleserver.repository.ScheduleRepository;
import com.example.scheduleserver.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 생성
    public ScheduleResponseDto addSchedule(String title, String contents, HttpSession session) {
        // 요청한 세션을 반환
        User sessionUser = (User) session.getAttribute("login");

        // 세션으로 가져온 user정보를 함께 포함하여 내용 작성
        Schedule getSchedule = new Schedule(title, contents, sessionUser);

        // DB에 저장
        Schedule savedSchedule = scheduleRepository.save(getSchedule);

        // 저장된 session을 기반으로 user를 가져와 현재 저장된 유저 정보 반환
        // user 정보 update가 됐을 경우를 위해 새로 반환
        User writer = userRepository.findByIdOrElseThrow(sessionUser.getId());
        return new ScheduleResponseDto(writer.getName(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getCreatedDate(), savedSchedule.getModifiedDate());
    }

    // 전체 조회
    public List<ScheduleResponseDto> getScheduleList() {
        List<ScheduleResponseDto> scheduleList = scheduleRepository.findAll().stream()
                .map(ScheduleResponseDto::toDto)
                .toList();
        return scheduleList;
    }

    // 선택 조회
    public ScheduleResponseDto findByIdSchedule(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(findSchedule.getUser().getName(), findSchedule.getTitle(), findSchedule.getContents(), findSchedule.getCreatedDate(), findSchedule.getModifiedDate());
    }


    // 선택 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String title, String contents, HttpSession session) {
        // 요청한 사람의 정보를 반환
        User sessionUser = (User) session.getAttribute("login");


        // 요청한 일정 반환
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 요청한 사람과, 선택한 일정의 작성자가 동일한지 확인
        validateSessionUser(sessionUser.getId(), findSchedule.getUser().getId());

        // 변경하지 않는 내용이 있으면(null인 경우) 이전 내용과 동일하게 유지
        String updateTitle = findSchedule.getTitle();
        String updateContents = findSchedule.getContents();
        if(title!=null){
            updateTitle = title;
        }
        if (contents!=null) {
            updateContents = contents;
        }

        findSchedule.update(updateTitle, updateContents);
        return new ScheduleResponseDto(findSchedule.getUser().getName(), findSchedule.getTitle(), findSchedule.getContents(), findSchedule.getCreatedDate(), findSchedule.getModifiedDate());
    }


    // 선택 일정 삭제
    public void deleteSchedule(Long id, HttpSession session) {
        User sessionUser = (User) session.getAttribute("login");

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        validateSessionUser(sessionUser.getId(), findSchedule.getUser().getId());

        // 요청한 유저와 작성자가 일치하면 글 삭제
        scheduleRepository.delete(findSchedule);
    }


    // 일정을 작성한 사람과 요청한 사람이 동일한지 확인
    private void validateSessionUser(Long sessionUserId, Long scheduleUserId){
        if(! sessionUserId.equals(scheduleUserId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Access.");
        }
    }

}
