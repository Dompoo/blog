package dompoo.blog.controller;

import dompoo.blog.service.CommentService;
import dompoo.blog.response.CommentResponseDto;
import dompoo.blog.request.comment.CommentSaveDto;
import dompoo.blog.request.comment.CommentUpdateDto;
import dompoo.blog.request.form.CommentCreateForm;
import dompoo.blog.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save/{writingId}")
    public String saveComment(
            @Valid CommentCreateForm commentCreateForm,
            @PathVariable("writingId") Long writingId,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "writing_detail";
        }
        CommentResponseDto saveComment = commentService.saveComment(new CommentSaveDto(
                commentCreateForm.getContent(),
                writingId,
                memberService.findByUsername(principal.getName()).getId()
        ));
        return String.format("redirect:/writing/%s#comment_%s", writingId, saveComment.getCommentId());
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
        CommentResponseDto updateComment = commentService.updateComment(new CommentUpdateDto(commentId, commentCreateForm.getContent()));
        return String.format("redirect:/writing/%s#comment_%s", updateComment.getWritingId(), updateComment.getCommentId());
    }

    //comment/delete/${comment.id}
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{commentId}")
    public String deleteComment(
            @PathVariable("commentId") Long commentId,
            Principal principal
    ) {
        CommentResponseDto findComment = commentService.findById(commentId);
        if (!findComment.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        commentService.deleteComment(commentId);
        return String.format("redirect:/writing/%s", findComment.getWritingId());
    }
}
