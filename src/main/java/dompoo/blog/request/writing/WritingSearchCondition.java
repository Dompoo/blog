package dompoo.blog.request.writing;

import lombok.Getter;

@Getter
public class WritingSearchCondition {

    private final String username;
    private final String writingTitle;
    private final String writingContent;

    public WritingSearchCondition(String username, String writingTitle, String writingContent) {
        this.username = username;
        this.writingTitle = writingTitle;
        this.writingContent = writingContent;
    }
}
