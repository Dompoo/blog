package dompoo.blog.writing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WritingUpdateDto {

    private Long writingId;
    private String subject;
    private String content;

    public WritingUpdateDto(Long writingId, String subject, String content) {
        this.writingId = writingId;
        this.subject = subject;
        this.content = content;
    }
}
