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
        MemberResponseDto memberDto1 = memberService.join(new MemberSaveDto("username1", "password1"));
        MemberResponseDto memberDto2 = memberService.join(new MemberSaveDto("username2", "password2"));

        WritingResponseDto writingDto1 = writingService.saveWrite(new WritingSaveDto("title1", "writingContent1", memberDto1.getId()));
        WritingResponseDto writingDto2 = writingService.saveWrite(new WritingSaveDto("title2", "writingContent2", memberDto1.getId()));
        WritingResponseDto writingDto3 = writingService.saveWrite(new WritingSaveDto("title3", "writingContent3", memberDto1.getId()));
        WritingResponseDto writingDto4 = writingService.saveWrite(new WritingSaveDto("title4", "writingContent4", memberDto2.getId()));

        commentService.saveComment(new CommentSaveDto("commentContent", writingDto1.getId(), memberDto1.getId()));
        commentService.saveComment(new CommentSaveDto("commentContent", writingDto1.getId(), memberDto1.getId()));
        commentService.saveComment(new CommentSaveDto("commentContent", writingDto1.getId(), memberDto2.getId()));
        commentService.saveComment(new CommentSaveDto("commentContent", writingDto2.getId(), memberDto1.getId()));
        commentService.saveComment(new CommentSaveDto("commentContent", writingDto3.getId(), memberDto2.getId()));
        commentService.saveComment(new CommentSaveDto("commentContent", writingDto4.getId(), memberDto2.getId()));
    }
}
