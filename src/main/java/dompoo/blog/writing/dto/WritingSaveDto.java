package dompoo.blog.writing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WritingSaveDto {

    private String subject;
    private String content;

    public WritingSaveDto(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
