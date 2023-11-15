package dompoo.blog.request.writing;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
