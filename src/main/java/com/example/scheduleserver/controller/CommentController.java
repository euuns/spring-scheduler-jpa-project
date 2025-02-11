package com.example.scheduleserver.controller;

import com.example.scheduleserver.dto.comment.CommentRequestDto;
import com.example.scheduleserver.dto.comment.CommentResponseDto;
import com.example.scheduleserver.entity.User;
import com.example.scheduleserver.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;


    // 댓글 작성 -> /schedule/{id} 에 댓글을 작성
    @PostMapping("/{scheduleId}")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long scheduleId,
                                                         @Validated @RequestBody CommentRequestDto requestDto,
                                                         HttpServletRequest servletRequest) {
        User login = (User) servletRequest.getSession().getAttribute("login");
        CommentResponseDto comment = commentService.addComment(scheduleId, login, requestDto.getContents());
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }


    // 댓글 조회 -> /schedule/{id} 에 작성된 모든 댓글 /comment 조회
    @GetMapping("/{scheduleId}/comment")
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@PathVariable Long scheduleId,
                                                                   @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo) {
        List<CommentResponseDto> commentList = commentService.getCommentList(scheduleId, pageNo);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }


    // 댓글 수정 -> /schedule/{id}/comment/{id} 댓글을 수정
    // 댓글을 작성한 사람만 수정 가능
    @PutMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                            @Validated @RequestBody CommentRequestDto requestDto,
                                                            HttpServletRequest servletRequest) {
        User login = (User) servletRequest.getSession().getAttribute("login");
        CommentResponseDto updateComment = commentService.updateComment(commentId, login, requestDto.getContents());
        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }


    // 댓글 삭제 -> /schedule/{id}/comment/{id} 댓글 삭제
    // 댓글을 작성한 사람만 삭제 가능
    @DeleteMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              HttpServletRequest servletRequest) {
        User login = (User) servletRequest.getSession().getAttribute("login");
        commentService.deleteComment(commentId, login);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
