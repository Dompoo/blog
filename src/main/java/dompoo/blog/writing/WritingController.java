package dompoo.blog.writing;

import dompoo.blog.comment.form.CommentCreateForm;
import dompoo.blog.member.MemberService;
import dompoo.blog.writing.dto.WritingResponseDto;
import dompoo.blog.writing.dto.WritingSaveDto;
import dompoo.blog.writing.dto.WritingUpdateDto;
import dompoo.blog.writing.form.WritingCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/writing")
@Slf4j
public class WritingController {

    private final WritingService writingService;
    private final MemberService memberService;

    @GetMapping("")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("modifiedDate"));
        Page<WritingResponseDto> writingList = writingService.findAll(PageRequest.of(page, 10, Sort.by(sorts)));
        model.addAttribute("writingList", writingList);
        return "writing";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long writingId, CommentCreateForm commentCreateForm) {

        WritingResponseDto findWriting = writingService.findOne(writingId);
        model.addAttribute("writing", findWriting);
        return "writing_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/save")
    public String saveWrite(WritingCreateForm writingCreateForm) {

        return "writing_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save")
    public String saveWrite(@Valid WritingCreateForm writingCreateForm, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "writing_form";
        }

        writingService.saveWrite(new WritingSaveDto(
                writingCreateForm.getTitle(),
                writingCreateForm.getContent(),
                memberService.findByUsername(principal.getName()).getId()
        ));

        return "redirect:/writing";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(Model model, WritingCreateForm writingCreateForm, @PathVariable("id") Long writingId, Principal principal) {
        WritingResponseDto writing = writingService.findOne(writingId);
        if (!writing.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        model.addAttribute("title", writing.getTitle());
        model.addAttribute("content", writing.getContent());
        return "writing_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyWriting(@Valid WritingCreateForm writingCreateForm, BindingResult bindingResult, @PathVariable("id") Long writingId, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "writing_form";
        }
        WritingResponseDto writing = writingService.findOne(writingId);
        if (!writing.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        writingService.updateWriting(new WritingUpdateDto(writingId, writingCreateForm.getTitle(), writingCreateForm.getContent()));

        return String.format("redirect:/writing/%s", writingId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteWriting(@PathVariable("id") Long writingId, Principal principal) {
        WritingResponseDto writing = writingService.findOne(writingId);
        if (!writing.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        writingService.deleteWriting(writingId);
        return "redirect:/";
    }
}
