package dompoo.blog.response;

import dompoo.blog.domain.Comment;
import dompoo.blog.domain.Member;
import dompoo.blog.etc.Reply;
import dompoo.blog.domain.Writing;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyResponseDto {

    private Long id;
    private String content;
    private LocalDateTime modifiedDate;
    private Member member;
    private Writing writing;
    private Comment comment;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.modifiedDate = reply.getModifiedDate();
        this.member = reply.getMember();
        this.writing = reply.getWriting();
        this.comment = reply.getComment();
    }
}
