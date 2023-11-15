package dompoo.blog.request.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WritingCreateForm {

    @Size(max = 30, message = "제목은 30자를 초과할 수 없습니다.")
    @NotEmpty(message = "제목은 비울 수 없습니다.")
    private String title;

    @NotEmpty(message = "내용은 비울 수 없습니다.")
    private String content;

}
