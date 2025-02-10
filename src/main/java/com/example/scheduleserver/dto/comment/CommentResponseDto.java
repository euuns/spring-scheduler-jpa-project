package com.example.scheduleserver.dto.comment;

import com.example.scheduleserver.entity.Comment;
import com.example.scheduleserver.entity.Schedule;
import com.example.scheduleserver.entity.User;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final String userName;
    private final String scheduleTitle;
    private final String contents;
    private final String createdDate;
    private final String modifiedDate;

    public CommentResponseDto(String userName, String scheduleTitle, String contents, String createdDate, String modifiedDate) {
        this.userName = userName;
        this.scheduleTitle = scheduleTitle;
        this.contents = contents;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static CommentResponseDto toDto(Comment comment){
        User getUser = comment.getUser();
        Schedule getSchedule = comment.getSchedule();

        return new CommentResponseDto(getUser.getName(), getSchedule.getTitle(), comment.getContents(),
                comment.getCreatedDate(), comment.getModifiedDate());
    }

    public CommentResponseDto(Comment comment){
        this.userName = comment.getUser().getName();
        this.scheduleTitle = comment.getSchedule().getTitle();
        this.contents = comment.getContents();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
