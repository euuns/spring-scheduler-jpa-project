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
import org.springframework.data.domain.PageImpl;
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
        List<Comment> findByScheduleComment = commentRepository.findAllByScheduleId(scheduleId);

        // List를 Page로 변환
        Page<Comment> commentPage = listToPage(findByScheduleComment, pageable);

        return commentPage.stream().map(CommentResponseDto::toDto).toList();
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


    // List를 Page로 변환
    private Page<Comment> listToPage(List<Comment> list, Pageable pageable) {
        // 페이지 범위 지정
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        //offset으로 지정한 페이지 범위가 벗어나는 경우 예외 처리
        validateRequestPage(start, end);

        Page<Comment> commentPage = new PageImpl<>(list.subList(start, end), pageable, list.size());

        // Page로 반환
        return commentPage;
    }

    private void validateRequestPage(int start, int end) {
        if (end < start) {
            throw new ValidException(ExceptionCode.PAGE_OVER);
        }
    }
}
