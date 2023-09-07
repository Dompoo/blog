package dompoo.blog;

import dompoo.blog.comment.CommentService;
import dompoo.blog.comment.dto.CommentSaveDto;
import dompoo.blog.member.MemberService;
import dompoo.blog.member.dto.MemberResponseDto;
import dompoo.blog.member.dto.MemberSaveDto;
import dompoo.blog.writing.WritingService;
import dompoo.blog.writing.dto.WritingResponseDto;
import dompoo.blog.writing.dto.WritingSaveDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlogInitializer {

    private final CommentService commentService;
    private final WritingService writingService;
    private final MemberService memberService;

    @Autowired
    public BlogInitializer(CommentService commentService, WritingService writingService, MemberService memberService) {
        this.commentService = commentService;
        this.writingService = writingService;
        this.memberService = memberService;
    }

    @PostConstruct
    public void init() {
        MemberResponseDto memberDto1 = memberService.join(new MemberSaveDto("윤동주", "password1"));
        MemberResponseDto memberDto2 = memberService.join(new MemberSaveDto("나태주", "password2"));

        WritingResponseDto writingDto1 = writingService.saveWrite(new WritingSaveDto(
                "서시",
                "죽는 날까지 하늘을 우러러\n" +
                        "한 점 부끄럼이 없기를,\n" +
                        "잎새에 이는 바람에도 \n" +
                        "나는 괴로워했다.\n" +
                        "별을 노래하는 마음으로 \n" +
                        "모든 죽어가는 것을 사랑해야지\n" +
                        "그리고 나한테 주어진 길을 \n" +
                        "걸어가야겠다.\n\n" +
                        "오늘 밤에도 별이 바람에 스치운다.",
                memberDto1.getId()));
        WritingResponseDto writingDto2 = writingService.saveWrite(new WritingSaveDto(
                "내가 좋아하는 사람",
                "내가 좋아하는 사람은\n" +
                        "슬퍼할 일을 마땅히 슬퍼하고\n" +
                        "괴로워할 일을 마땅히\n" +
                        "괴로워하는 사람\n\n" +
                        "남의 앞에 섰을 때\n" +
                        "교만하지 않고\n" +
                        "남의 뒤에 섰을 때\n" +
                        "비굴하지 않은 사람\n" +
                        "\n" +
                        "내가 좋아하는 사람은\n" +
                        "미워할 것을 마땅히 미워하고\n" +
                        "사랑할 것을 마땅히 사랑하는\n" +
                        "그저 보통의 사람",
                memberDto2.getId()));
        for (int i = 0; i < 100; i++) {
            writingService.saveWrite(new WritingSaveDto("writing" + i, "content" + i, memberDto1.getId()));
        }

        commentService.saveComment(new CommentSaveDto("님 짱이네요.", writingDto1.getId(), memberDto2.getId()));
        commentService.saveComment(new CommentSaveDto("님 넘 멋져요.", writingDto2.getId(), memberDto1.getId()));
    }
}
