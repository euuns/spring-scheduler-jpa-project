package com.example.scheduleserver.service;

import com.example.scheduleserver.dto.comment.CommentResponseDto;
import com.example.scheduleserver.entity.Comment;
import com.example.scheduleserver.entity.Schedule;
import com.example.scheduleserver.entity.User;
import com.example.scheduleserver.exception.ExceptionCode;
import com.example.scheduleserver.exception.ValidException;
import com.example.scheduleserver.repository.CommentRepository;
import com.example.scheduleserver.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService extends ValidateSessionService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    private final static int PAGE_SIZE = 20;

    // 댓글 작성
    public CommentResponseDto addComment(Long scheduleId, User user, String contents) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(schedule, user, contents);
        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(saveComment);
    }


    // 댓글 조회
    public List<CommentResponseDto> getCommentList(Long scheduleId, int pageNo) {
        if (pageNo < 0) {
            throw new ValidException(ExceptionCode.PAGE_NOT_POSITIVE);
        }
        // scheduleId가 존재하지 않으면 예외 처리
        scheduleRepository.findByIdOrElseThrow(scheduleId);

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);
        Page<Comment> findByScheduleComment = commentRepository.findAllByScheduleId(scheduleId, pageable);

        return findByScheduleComment.stream().map(CommentResponseDto::toDto).toList();
    }


    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, User user, String contents) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        // 요청자(login한 사람)와 댓글 작성자 비교
        validateSessionUser(user.getId(), comment.getUser().getId());

        comment.update(contents);
        return new CommentResponseDto(comment);
    }


    // 댓글 삭제
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        validateSessionUser(user.getId(), comment.getUser().getId());

        commentRepository.delete(comment);
    }
}
