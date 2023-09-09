package dompoo.blog.writing.dto;

import dompoo.blog.comment.Comment;
import dompoo.blog.writing.Writing;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class WritingResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private String username;
    private List<Comment> comments;

    public WritingResponseDto(Writing writing) {
        this.id = writing.getId();
        this.title = writing.getTitle();
        this.content = writing.getContent();
        this.createDate = writing.getModifiedDate();
        this.username = writing.getMember().getUsername();
        this.comments = writing.getComment();
    }
}
