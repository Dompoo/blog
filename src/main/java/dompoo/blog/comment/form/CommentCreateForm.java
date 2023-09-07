package dompoo.blog.comment.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateForm {

    @NotEmpty(message = "내용은 비울 수 없습니다.")
    private String content;
}
