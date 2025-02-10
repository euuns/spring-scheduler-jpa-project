package com.example.scheduleserver.repository;

import com.example.scheduleserver.entity.Schedule;
import com.example.scheduleserver.exception.ExceptionCode;
import com.example.scheduleserver.exception.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ValidException(ExceptionCode.SCHEDULE_NOT_FOUND));
    }
}
