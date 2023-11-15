package dompoo.blog.request.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateDto {

    private Long commentId;
    private String content;

    public CommentUpdateDto(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
