package dompoo.blog.comment;

import dompoo.blog.comment.dto.CommentResponseDto;
import dompoo.blog.writing.WritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final WritingService writingService;

//    @GetMapping("")
//    public String addComment(@RequestParam String content) {
//
//        WritingResponseDto dto = writingService.findOne(writingId);
//        new WritingSaveDto(dto.)
//        commentService.saveComment()
//
//        return "comment";
//    }

    @GetMapping("/list/{writingId}")
    public String list(Model model, @PathVariable("writingId") Long writingId) {

        Page<CommentResponseDto> findComment = commentService.findByWritingId(PageRequest.of(0, 10), writingId);
        model.addAttribute("commentList", findComment);
        return "comment";
    }

//    @PostMapping("/")
}
