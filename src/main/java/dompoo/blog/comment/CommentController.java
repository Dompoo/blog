package dompoo.blog.comment;

import dompoo.blog.comment.dto.CommentResponseDto;
import dompoo.blog.comment.dto.CommentSaveDto;
import dompoo.blog.comment.form.CommentCreateForm;
import dompoo.blog.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    @GetMapping("/{writingId}")
    public String list(
            Model model,
            @PathVariable("writingId") Long writingId,
            CommentCreateForm commentCreateForm
    ) {
        Page<CommentResponseDto> findComment = commentService.findByWritingId(PageRequest.of(0, 10), writingId);
        model.addAttribute("commentList", findComment);
        model.addAttribute("writingId", writingId);
        return "comment";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{writingId}")
    public String saveComment(
            @PathVariable("writingId") Long writingId,
            @Valid CommentCreateForm commentCreateForm,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "comment";
        }

        commentService.saveComment(new CommentSaveDto(
                commentCreateForm.getContent(),
                writingId,
                memberService.findByUsername(principal.getName()).getId()
        ));

        return "redirect:/comment/" + writingId;
    }
}
