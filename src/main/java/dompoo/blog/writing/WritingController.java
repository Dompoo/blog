package dompoo.blog.writing;

import dompoo.blog.writing.dto.WritingResponseDto;
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
@RequestMapping("/writing")
public class WritingController {

    private final WritingService writingService;

    @GetMapping("")
    public String list(Model model) {
        Page<WritingResponseDto> writingList = writingService.findAll(PageRequest.of(0, 10));
        model.addAttribute("writingList", writingList);
        return "writing";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long writingId) {
        WritingResponseDto findWriting = writingService.findOne(writingId);
        model.addAttribute("writing", findWriting);
        return "writing_detail";
    }

}
