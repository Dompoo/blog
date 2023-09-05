package dompoo.blog.comment.dto;

import dompoo.blog.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createDate;
    private Long memberId;
    private Long writingId;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createDate = comment.getCreateDate();
        this.memberId = comment.getMember().getId();
        this.writingId = comment.getWriting().getId();
    }
}
