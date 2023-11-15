package dompoo.blog.request.reply;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyUpdateDto {

    private Long replyId;
    private String content;

    public ReplyUpdateDto(Long replyId, String content) {
        this.replyId = replyId;
        this.content = content;
    }
}
