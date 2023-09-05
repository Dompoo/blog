package dompoo.blog.writing;

import dompoo.blog.writing.dto.WritingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WritingController {

    private final WritingService writingService;

    @GetMapping("/writing/list")
    public String list(Model model) {
        Page<WritingResponseDto> writingList = writingService.findAll(PageRequest.of(0, 10));
        model.addAttribute("writingList", writingList);
        return "writings";
    }
}
