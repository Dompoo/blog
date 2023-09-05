package dompoo.blog.writing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WritingSaveDto {

    private String subject;
    private String content;
    private Long memberId;

    public WritingSaveDto(String subject, String content, Long memberId) {
        this.subject = subject;
        this.content = content;
        this.memberId = memberId;
    }
}
