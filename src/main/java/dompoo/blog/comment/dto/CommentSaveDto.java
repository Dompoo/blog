package dompoo.blog.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveDto {

    private String content;
    private Long writingId;
    private Long memberId;

    public CommentSaveDto(String content, Long writingId, Long memberId) {
        this.content = content;
        this.writingId = writingId;
        this.memberId = memberId;
    }
}
