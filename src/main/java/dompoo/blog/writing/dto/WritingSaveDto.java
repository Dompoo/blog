package dompoo.blog.writing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WritingSaveDto {

    private String username;
    private String subject;
    private String content;
}
