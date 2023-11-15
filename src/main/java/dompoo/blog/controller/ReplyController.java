package dompoo.blog.controller;

import dompoo.blog.service.ReplyService;
import dompoo.blog.service.MemberService;
import dompoo.blog.response.ReplyResponseDto;
import dompoo.blog.request.reply.ReplySaveDto;
import dompoo.blog.request.reply.ReplyUpdateDto;
import dompoo.blog.request.form.ReplyCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{replyId}")
    public String modify(
            ReplyCreateForm replyCreateForm,
            @PathVariable("replyId") Long replyId,
            Principal principal
    ) {
        ReplyResponseDto findReply = replyService.findById(replyId);
        if (!findReply.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        replyCreateForm.setContent(findReply.getContent());
        return "reply_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{replyId}")
    public String modifyReply(
            @Valid ReplyCreateForm replyCreateForm,
            BindingResult bindingResult,
            @PathVariable("replyId") Long replyId,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "reply_form";
        }
        ReplyResponseDto findReply = replyService.findById(replyId);
        if (!findReply.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        ReplyResponseDto updateReply = replyService.updateReply(new ReplyUpdateDto(replyId, replyCreateForm.getContent()));
        return String.format("redirect:/writing/%s#comment_%s", updateReply.getWriting().getId(), updateReply.getComment().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{replyId}")
    public String deleteReply(
            @PathVariable("replyId") Long replyId,
            Principal principal
    ) {
        ReplyResponseDto findReply = replyService.findById(replyId);
        if (!findReply.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        replyService.deleteReply(replyId);
        return String.format("redirect:/writing/%s", findReply.getWriting().getId());
    }
}
