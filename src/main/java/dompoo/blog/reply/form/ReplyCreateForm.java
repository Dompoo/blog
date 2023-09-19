package dompoo.blog.reply.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyCreateForm {

    @NotEmpty(message = "내용은 비울 수 없습니다.")
    private String content;
}
