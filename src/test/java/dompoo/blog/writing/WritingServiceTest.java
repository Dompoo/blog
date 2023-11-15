package dompoo.blog.writing;

import dompoo.blog.service.MemberService;
import dompoo.blog.response.MemberResponseDto;
import dompoo.blog.request.member.MemberSaveDto;
import dompoo.blog.service.WritingService;
import dompoo.blog.response.WritingResponseDto;
import dompoo.blog.request.writing.WritingSaveDto;
import dompoo.blog.request.writing.WritingUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
class WritingServiceTest {

    private final WritingService writingService;
    private final MemberService memberService;

//    private PageRequest defaultPageRequest = PageRequest.of(0, 10);

    @Autowired
    public WritingServiceTest(WritingService writingService, MemberService memberService) {
        this.writingService = writingService;
        this.memberService = memberService;
    }

    @Test
    void saveWrite() {
        MemberSaveDto member1 = new MemberSaveDto("username", "password");
        MemberResponseDto memberDto = memberService.join(member1);

        WritingSaveDto writingSaveDto = new WritingSaveDto("title", "content", memberDto.getId());
        WritingResponseDto writingResponseDto = writingService.saveWrite(writingSaveDto);

        assertThat(writingResponseDto.getTitle()).isEqualTo(writingSaveDto.getSubject());
        assertThat(writingResponseDto.getContent()).isEqualTo(writingSaveDto.getTitle());
        assertThat(writingResponseDto.getUsername()).isEqualTo(memberDto.getUsername());
    }

    @Test
    void findAll() {
        MemberSaveDto member = new MemberSaveDto("username", "password");
        MemberResponseDto memberDto = memberService.join(member);

        WritingSaveDto writingSaveDto1 = new WritingSaveDto("title1", "content1", memberDto.getId());
        WritingSaveDto writingSaveDto2 = new WritingSaveDto("title2", "content2", memberDto.getId());
        WritingSaveDto writingSaveDto3 = new WritingSaveDto("title3", "content3", memberDto.getId());
        writingService.saveWrite(writingSaveDto1);
        writingService.saveWrite(writingSaveDto2);
        writingService.saveWrite(writingSaveDto3);

        Page<WritingResponseDto> findWriting = writingService.findAll(PageRequest.of(0, 10));

        assertThat(findWriting.getTotalElements()).isEqualTo(3);
    }

    @Test
    void findOne() {
        MemberSaveDto member = new MemberSaveDto("username", "password");
        MemberResponseDto memberDto = memberService.join(member);

        WritingSaveDto writingSaveDto1 = new WritingSaveDto("title1", "content1", memberDto.getId());
        WritingSaveDto writingSaveDto2 = new WritingSaveDto("title2", "content2", memberDto.getId());
        WritingSaveDto writingSaveDto3 = new WritingSaveDto("title3", "content3", memberDto.getId());
        WritingResponseDto writingResponseDto = writingService.saveWrite(writingSaveDto1);
        writingService.saveWrite(writingSaveDto2);
        writingService.saveWrite(writingSaveDto3);

        WritingResponseDto findWriting = writingService.findOne(writingResponseDto.getId());

        assertThat(findWriting.getTitle()).isEqualTo(writingSaveDto1.getSubject());
        assertThat(findWriting.getContent()).isEqualTo(writingSaveDto1.getTitle());
    }

    @Test
    void findBySubject() {
        MemberSaveDto member = new MemberSaveDto("username", "password");
        MemberResponseDto memberDto = memberService.join(member);

        WritingSaveDto writingSaveDto1 = new WritingSaveDto("title1", "content1", memberDto.getId());
        WritingSaveDto writingSaveDto2 = new WritingSaveDto("title2", "content2", memberDto.getId());
        WritingSaveDto writingSaveDto3 = new WritingSaveDto("title3", "content3", memberDto.getId());
        writingService.saveWrite(writingSaveDto1);
        writingService.saveWrite(writingSaveDto2);
        writingService.saveWrite(writingSaveDto3);

        Page<WritingResponseDto> findWriting = writingService.findBySubject(PageRequest.of(0, 10), "title1");

        assertThat(findWriting.getTotalElements()).isEqualTo(1);
    }

    @Test
    void findByUsername() {
        MemberSaveDto member1 = new MemberSaveDto("username1", "password");
        MemberSaveDto member2 = new MemberSaveDto("username2", "password");
        MemberResponseDto memberDto1 = memberService.join(member1);
        MemberResponseDto memberDto2 = memberService.join(member2);

        WritingSaveDto writingSaveDto1 = new WritingSaveDto("title1", "content1", memberDto1.getId());
        WritingSaveDto writingSaveDto2 = new WritingSaveDto("title2", "content2", memberDto1.getId());
        WritingSaveDto writingSaveDto3 = new WritingSaveDto("title3", "content3", memberDto2.getId());
        writingService.saveWrite(writingSaveDto1);
        writingService.saveWrite(writingSaveDto2);
        writingService.saveWrite(writingSaveDto3);

        Page<WritingResponseDto> findWritings = writingService.findByUsername(PageRequest.of(0, 10), memberDto1.getUsername());

        assertThat(findWritings.getTotalElements()).isEqualTo(2);
    }


    @Test
    void updateWriting() {
        MemberSaveDto member = new MemberSaveDto("username1", "password");
        MemberResponseDto memberDto = memberService.join(member);

        WritingSaveDto writing = new WritingSaveDto("title", "content", memberDto.getId());

        WritingResponseDto writingResponseDto = writingService.saveWrite(writing);
        WritingUpdateDto writingUpdateDto = new WritingUpdateDto(writingResponseDto.getId(), "new title", "new content");
        writingService.updateWriting(writingUpdateDto);

        WritingResponseDto findWriting = writingService.findOne(writingResponseDto.getId());

        assertThat(findWriting.getTitle()).isEqualTo("new title");
        assertThat(findWriting.getContent()).isEqualTo("new content");
    }
}