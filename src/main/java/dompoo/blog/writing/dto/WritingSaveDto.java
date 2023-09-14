package dompoo.blog.writing.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WritingSaveDto {

    private String subject;
    private String title;
    private Long memberId;

    public WritingSaveDto(String title, String content, Long memberId) {
        this.subject = title;
        this.title = content;
        this.memberId = memberId;
    }
}
