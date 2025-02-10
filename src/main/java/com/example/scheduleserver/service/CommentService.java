package com.example.scheduleserver.service;

import com.example.scheduleserver.dto.comment.CommentResponseDto;
import com.example.scheduleserver.entity.Comment;
import com.example.scheduleserver.entity.Schedule;
import com.example.scheduleserver.entity.User;
import com.example.scheduleserver.exception.ExceptionCode;
import com.example.scheduleserver.exception.ValidException;
import com.example.scheduleserver.repository.CommentRepository;
import com.example.scheduleserver.repository.ScheduleRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    // 댓글 작성
    public CommentResponseDto addComment(Long scheduleId, HttpSession session, String contents) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        User login = (User) session.getAttribute("login");

        Comment comment = new Comment(schedule, login, contents);
        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(saveComment);
    }


    // 댓글 조회
    public List<CommentResponseDto> getCommentList(Long scheduleId) {
        // scheduleId를 이용해 schedule을 조건으로 commentList 반환
        List<Comment> findBySchedule = commentRepository.findAllByScheduleId(scheduleId);

        // Comment를 CommentResponseDto로 변환
        List<CommentResponseDto> commentList = findBySchedule.stream()
                .map(CommentResponseDto::toDto)
                .toList();

        return commentList;
    }


    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, HttpSession session, String contents) {
        User login = (User) session.getAttribute("login");
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        // 요청자(login한 사람)와 댓글 작성자 비교
        validateSessionUser(login.getId(), comment.getUser().getId());

        comment.update(contents);
        return new CommentResponseDto(comment);
    }


    // 댓글 삭제
    public void deleteComment(Long commentId, HttpSession session) {
        User login = (User) session.getAttribute("login");
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        validateSessionUser(login.getId(), comment.getUser().getId());

        commentRepository.delete(comment);
    }


    // 요청-작성자 비교
    private void validateSessionUser(Long sessionUserId, Long commentUserId) {
        if (!sessionUserId.equals(commentUserId)) {
            throw new ValidException(ExceptionCode.SESSION_NOT_VALID);
        }
    }
}
