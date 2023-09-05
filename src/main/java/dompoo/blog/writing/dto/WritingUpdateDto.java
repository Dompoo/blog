package dompoo.blog.writing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WritingUpdateDto {

    private Long writingId;
    private String title;
    private String content;

    public WritingUpdateDto(Long writingId, String title, String content) {
        this.writingId = writingId;
        this.title = title;
        this.content = content;
    }
}
