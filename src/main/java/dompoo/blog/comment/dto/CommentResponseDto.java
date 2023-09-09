package dompoo.blog.comment.dto;

import dompoo.blog.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private LocalDateTime createDate;
    private String username;
    private String writingTitle;
    private Long writingId;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createDate = comment.getModifiedDate();
        this.username = comment.getMember().getUsername();
        this.writingTitle = comment.getWriting().getTitle();
        this.writingId = comment.getWriting().getId();
    }
}
