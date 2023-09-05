package dompoo.blog.comment;

import dompoo.blog.comment.dto.CommentResponseDto;
import dompoo.blog.comment.dto.CommentSaveDto;
import dompoo.blog.member.MemberService;
import dompoo.blog.member.dto.MemberResponseDto;
import dompoo.blog.member.dto.MemberSaveDto;
import dompoo.blog.writing.WritingService;
import dompoo.blog.writing.dto.WritingResponseDto;
import dompoo.blog.writing.dto.WritingSaveDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CommentServiceTest {

    private final CommentService commentService;
    private final WritingService writingService;
    private final MemberService memberService;

    @Autowired
    public CommentServiceTest(CommentService commentService, WritingService writingService, MemberService memberService) {
        this.commentService = commentService;
        this.writingService = writingService;
        this.memberService = memberService;
    }

    @Test
    void saveComment() {
        MemberResponseDto memberDto = memberService.join(new MemberSaveDto("username", "password"));
        WritingResponseDto writingDto = writingService.saveWrite(new WritingSaveDto("title", "writingContent", memberDto.getId()));
        CommentSaveDto commentSaveDto = new CommentSaveDto("commentContent", writingDto.getId(), memberDto.getId());

        CommentResponseDto commentDto = commentService.saveComment(commentSaveDto);

        assertThat(commentDto.getContent()).isEqualTo(commentSaveDto.getContent());
        assertThat(commentDto.getUsername()).isEqualTo(memberDto.getUsername());
        assertThat(commentDto.getWritingTitle()).isEqualTo(writingDto.getTitle());
    }

    @Test
    void findByWritingId() {
        MemberResponseDto memberDto = memberService.join(new MemberSaveDto("username", "password"));
        WritingResponseDto writingDto = writingService.saveWrite(new WritingSaveDto("title", "writingContent", memberDto.getId()));
        CommentResponseDto commentDto = commentService.saveComment(new CommentSaveDto("commentContent", writingDto.getId(), memberDto.getId()));

        CommentResponseDto findComment = commentService.findByWritingId(PageRequest.of(0, 10), writingDto.getId()).stream().findFirst().orElse(null);

        assertThat(findComment.getContent()).isEqualTo(commentDto.getContent());
    }
}