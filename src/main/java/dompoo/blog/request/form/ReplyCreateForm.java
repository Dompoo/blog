package dompoo.blog.request.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyCreateForm {

    @NotEmpty(message = "대댓글은 비울 수 없습니다.")
    private String content;
}
