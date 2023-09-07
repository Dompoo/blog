package dompoo.blog.writing.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WritingCreateForm {

    @Size(min = 3, max = 30)
    @NotEmpty(message = "제목은 비울 수 없습니다.")
    private String title;

    @NotEmpty(message = "내용은 비울 수 없습니다.")
    private String content;

}
