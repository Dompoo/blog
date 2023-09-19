package dompoo.blog.reply.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplySaveDto {

    private String content;
    private Long memberId;
    private Long writingId;
    private Long commentId;

    public ReplySaveDto(String content, Long memberId, Long writingId, Long commentId) {
        this.content = content;
        this.memberId = memberId;
        this.writingId = writingId;
        this.commentId = commentId;
    }
}
