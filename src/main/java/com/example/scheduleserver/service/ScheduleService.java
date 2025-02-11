package com.example.scheduleserver.service;

import com.example.scheduleserver.dto.schedule.ScheduleResponseDto;
import com.example.scheduleserver.entity.Schedule;
import com.example.scheduleserver.entity.User;
import com.example.scheduleserver.exception.ExceptionCode;
import com.example.scheduleserver.exception.ValidException;
import com.example.scheduleserver.repository.ScheduleRepository;
import com.example.scheduleserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService extends ValidateSessionService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 페이지 기본 사이즈
    private final static int PAGE_SIZE = 10;

    // 일정 생성
    public ScheduleResponseDto addSchedule(String title, String contents, User user) {
        // 세션으로 가져온 user정보를 함께 포함하여 내용 작성
        Schedule getSchedule = new Schedule(title, contents, user);

        // DB에 저장
        Schedule savedSchedule = scheduleRepository.save(getSchedule);

        // 저장된 session을 기반으로 user를 가져와 현재 저장된 유저 정보 반환
        // user 정보 update가 됐을 경우를 위해 새로 반환
        User writer = userRepository.findByIdOrElseThrow(user.getId());
        return new ScheduleResponseDto(writer.getName(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getCreatedDate(), savedSchedule.getModifiedDate());
    }

    // 전체 조회
    public List<ScheduleResponseDto> getScheduleList(int pageNo) {
        // 요청 페이지 번호가 음수인 경우
        if (pageNo < 0) {
            throw new ValidException(ExceptionCode.PAGE_NOT_POSITIVE);
        }

        Sort sort = Sort.by(Sort.Order.desc("modifiedDate"));
        // 요청에 맞는 페이지 정보 생성 후 반환
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);

        // 요청 페이지가 최대 페이지보다 클 경우
        if (schedulePage.getTotalPages() < pageNo + 1) {
            throw new ValidException(ExceptionCode.PAGE_OVER);
        }

        // Schedule을 ResponseDto로 변환하고, List로 바꿔서 반환
        return schedulePage.stream().map(ScheduleResponseDto::toDto).toList();
    }

    // 선택 조회
    public ScheduleResponseDto findByIdSchedule(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(findSchedule.getUser().getName(), findSchedule.getTitle(), findSchedule.getContents(), findSchedule.getCreatedDate(), findSchedule.getModifiedDate());
    }


    // 선택 일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String title, String contents, User user) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 요청한 사람과, 선택한 일정의 작성자가 동일한지 확인
        validateSessionUser(user.getId(), findSchedule.getUser().getId());

        // 변경하지 않는 내용이 있으면(null인 경우) 이전 내용과 동일하게 유지
        String updateTitle = findSchedule.getTitle();
        String updateContents = findSchedule.getContents();
        if (title != null) {
            updateTitle = title;
        }
        if (contents != null) {
            updateContents = contents;
        }

        findSchedule.update(updateTitle, updateContents);
        return new ScheduleResponseDto(findSchedule.getUser().getName(), findSchedule.getTitle(), findSchedule.getContents(), findSchedule.getCreatedDate(), findSchedule.getModifiedDate());
    }


    // 선택 일정 삭제
    public void deleteSchedule(Long id, User user) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        validateSessionUser(user.getId(), findSchedule.getUser().getId());

        // 요청한 유저와 작성자가 일치하면 글 삭제
        scheduleRepository.delete(findSchedule);
    }
}
