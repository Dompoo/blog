package dompoo.blog.writing;

import dompoo.blog.member.MemberService;
import dompoo.blog.member.dto.MemberResponseDto;
import dompoo.blog.member.dto.MemberSaveDto;
import dompoo.blog.writing.dto.WritingResponseDto;
import dompoo.blog.writing.dto.WritingSaveDto;
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

        WritingSaveDto writingSaveDto = new WritingSaveDto("subject", "content");
        WritingResponseDto writingResponseDto = writingService.saveWrite(writingSaveDto, memberDto.getId());

        assertThat(writingResponseDto.getSubject()).isEqualTo(writingSaveDto.getSubject());
        assertThat(writingResponseDto.getContent()).isEqualTo(writingSaveDto.getContent());
        assertThat(writingResponseDto.getUsername()).isEqualTo(memberDto.getUsername());
    }

    @Test
    void findAll() {
        MemberSaveDto member = new MemberSaveDto("username", "password");
        MemberResponseDto memberDto = memberService.join(member);

        WritingSaveDto writingSaveDto1 = new WritingSaveDto("subject1", "content1");
        WritingSaveDto writingSaveDto2 = new WritingSaveDto("subject2", "content2");
        WritingSaveDto writingSaveDto3 = new WritingSaveDto("subject3", "content3");
        writingService.saveWrite(writingSaveDto1, memberDto.getId());
        writingService.saveWrite(writingSaveDto2, memberDto.getId());
        writingService.saveWrite(writingSaveDto3, memberDto.getId());

        Page<WritingResponseDto> findWriting = writingService.findAll(PageRequest.of(0, 10));

        assertThat(findWriting.getTotalElements()).isEqualTo(3);
    }

    @Test
    void findBySubject() {
        MemberSaveDto member = new MemberSaveDto("username", "password");
        MemberResponseDto memberDto = memberService.join(member);

        WritingSaveDto writingSaveDto1 = new WritingSaveDto("subject1", "content1");
        WritingSaveDto writingSaveDto2 = new WritingSaveDto("subject2", "content2");
        WritingSaveDto writingSaveDto3 = new WritingSaveDto("subject3", "content3");
        writingService.saveWrite(writingSaveDto1, memberDto.getId());
        writingService.saveWrite(writingSaveDto2, memberDto.getId());
        writingService.saveWrite(writingSaveDto3, memberDto.getId());

        Page<WritingResponseDto> findWriting = writingService.findBySubject(PageRequest.of(0, 10), "subject1");

        assertThat(findWriting.getTotalElements()).isEqualTo(1);
    }

    @Test
    void findByUsername() {
        MemberSaveDto member1 = new MemberSaveDto("username1", "password");
        MemberSaveDto member2 = new MemberSaveDto("username2", "password");
        MemberResponseDto memberDto1 = memberService.join(member1);
        MemberResponseDto memberDto2 = memberService.join(member2);

        WritingSaveDto writingSaveDto1 = new WritingSaveDto("subject1", "content1");
        WritingSaveDto writingSaveDto2 = new WritingSaveDto("subject2", "content2");
        WritingSaveDto writingSaveDto3 = new WritingSaveDto("subject3", "content3");
        writingService.saveWrite(writingSaveDto1, memberDto1.getId());
        writingService.saveWrite(writingSaveDto2, memberDto1.getId());
        writingService.saveWrite(writingSaveDto3, memberDto2.getId());

        Page<WritingResponseDto> findWritings = writingService.findByUsername(PageRequest.of(0, 10), memberDto1.getUsername());

        assertThat(findWritings.getTotalElements()).isEqualTo(2);
    }
}