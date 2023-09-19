package dompoo.blog.reply;

import dompoo.blog.member.MemberService;
import dompoo.blog.reply.dto.ReplySaveDto;
import dompoo.blog.reply.form.ReplyCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save/{writingId}/{commentId}")
    public String saveReply(
            @Valid ReplyCreateForm replyCreateForm,
            @PathVariable("writingId") Long writingId,
            @PathVariable("commentId") Long commentId,
            BindingResult bindingResult,
            Principal principal
    ) {

        if (bindingResult.hasErrors()) {
            return "writing_detail";
        }
        replyService.addReply(new ReplySaveDto(
                replyCreateForm.getContent(),
                memberService.findByUsername(principal.getName()).getId(),
                writingId,
                commentId
        ));
        return String.format("redirect:/writing/%s#comment_%s", writingId, commentId);
    }

}
