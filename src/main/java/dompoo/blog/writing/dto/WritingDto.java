package dompoo.blog.writing.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class WritingDto {

    private String username;
    private String writingTitle;
    private String writingContent;

    @QueryProjection
    public WritingDto(String username, String writingTitle, String writingContent) {
        this.username = username;
        this.writingTitle = writingTitle;
        this.writingContent = writingContent;
    }
}
