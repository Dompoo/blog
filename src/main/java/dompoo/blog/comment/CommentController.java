package dompoo.blog.comment;

import dompoo.blog.comment.dto.CommentResponseDto;
import dompoo.blog.comment.dto.CommentSaveDto;
import dompoo.blog.comment.dto.CommentUpdateDto;
import dompoo.blog.comment.form.CommentCreateForm;
import dompoo.blog.member.MemberService;
import dompoo.blog.writing.WritingService;
import dompoo.blog.writing.dto.WritingResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final WritingService writingService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save/{writingId}")
    public String saveComment(
            Model model,
            @Valid CommentCreateForm commentCreateForm,
            @PathVariable("writingId") Long writingId,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "writing_detail";
        }
        commentService.saveComment(new CommentSaveDto(
                commentCreateForm.getContent(),
                writingId,
                memberService.findByUsername(principal.getName()).getId()
        ));
        WritingResponseDto findWriting = writingService.findOne(writingId);
        model.addAttribute("writing", findWriting);
        return "writing_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{commentId}")
    public String modify(
            CommentCreateForm commentCreateForm,
            @PathVariable("commentId") Long commentId,
            Principal principal
    ) {
        CommentResponseDto findComment = commentService.findById(commentId);
        if (!findComment.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentCreateForm.setContent(findComment.getContent());
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{commentId}")
    public String modifyComment(
            @Valid CommentCreateForm commentCreateForm,
            BindingResult bindingResult,
            @PathVariable("commentId") Long commentId,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }
        CommentResponseDto findComment = commentService.findById(commentId);
        if (!findComment.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentService.updateComment(new CommentUpdateDto(commentId, commentCreateForm.getContent()));
        return String.format("redirect:/writing/%s", findComment.getWritingId());
    }

    //comment/delete/${comment.id}

}
