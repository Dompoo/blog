package dompoo.blog.writing.dto;

import com.querydsl.core.annotations.QueryProjection;
import dompoo.blog.comment.Comment;
import dompoo.blog.writing.Writing;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WritingResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private String username;
    private List<Comment> comments;
    private int votes;

    public WritingResponseDto(Writing writing) {
        this.id = writing.getId();
        this.title = writing.getTitle();
        this.content = writing.getContent();
        this.createDate = writing.getModifiedDate();
        this.username = writing.getMember().getUsername();
        this.comments = writing.getComment();
        this.votes = writing.getVoteMembers().size();
    }

    @QueryProjection
    public WritingResponseDto(Long id, String title, String content, LocalDateTime createDate, String username, List<Comment> comments, int votes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.username = username;
        this.comments = comments;
        this.votes = votes;
    }
}
