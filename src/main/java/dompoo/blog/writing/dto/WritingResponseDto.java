package dompoo.blog.writing.dto;

import dompoo.blog.writing.Writing;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WritingResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private String username;

    public WritingResponseDto(Writing writing) {
        this.id = writing.getId();
        this.title = writing.getTitle();
        this.content = writing.getContent();
        this.createDate = writing.getModifiedDate();
        this.username = writing.getMember().getUsername();
    }
}
