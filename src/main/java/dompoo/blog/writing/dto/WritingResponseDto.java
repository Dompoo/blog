package dompoo.blog.writing.dto;

import dompoo.blog.writing.Writing;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WritingResponseDto {

    private Long id;
    private String subject;
    private String content;
    private LocalDateTime createDate;
    private String username;

    public WritingResponseDto(Writing writing) {
        this.id = writing.getId();
        this.subject = writing.getSubject();
        this.content = writing.getContent();
        this.createDate = writing.getCreateDate();
        this.username = writing.getMember().getUsername();
    }
}
