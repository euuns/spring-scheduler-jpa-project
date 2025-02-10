package com.example.scheduleserver.repository;

import com.example.scheduleserver.entity.Comment;
import com.example.scheduleserver.exception.ExceptionCode;
import com.example.scheduleserver.exception.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ValidException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    List<Comment> findAllByScheduleId(long scheduleId);
}
