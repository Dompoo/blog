package dompoo.blog.member;

import dompoo.blog.domain.Member;
import dompoo.blog.response.MemberResponseDto;
import dompoo.blog.request.member.MemberSaveDto;
import dompoo.blog.request.member.MemberUpdateDto;
import dompoo.blog.repository.MemberRepository;
import dompoo.blog.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceTest(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @Test
    void join() {
        MemberSaveDto member1 = new MemberSaveDto("member1", "password1");
        MemberSaveDto member2 = new MemberSaveDto("member2", "password2");
        memberService.join(member1);
        memberService.join(member2);

        assertThat(memberRepository.count()).isEqualTo(2);
    }

    @Test
    void findAll() {
        MemberSaveDto member1 = new MemberSaveDto("member1", "password1");
        MemberSaveDto member2 = new MemberSaveDto("member2", "password2");
        memberService.join(member1);
        memberService.join(member2);

        Page<MemberResponseDto> findMembers = memberService.findAll(PageRequest.of(0, 10));

        assertThat(findMembers.getTotalElements()).isEqualTo(2);
    }

    @Test
    void findOne() {
        MemberSaveDto member = new MemberSaveDto("member1", "password1");
        MemberResponseDto saveMember = memberService.join(member);

        MemberResponseDto findMember = memberService.findOne(saveMember.getId());

        assertThat(findMember.getId()).isEqualTo(saveMember.getId());
    }

    @Test
    void update() {
        MemberSaveDto member = new MemberSaveDto("member1", "password1");
        MemberResponseDto saveMember = memberService.join(member);

        memberService.update(saveMember.getId(), new MemberUpdateDto("updateName", "updatePassword"));
        Member findMember = memberRepository.findById(saveMember.getId()).get();
        assertThat(findMember.getUsername()).isEqualTo("updateName");
        assertThat(findMember.getPassword()).isEqualTo("updatePassword");
    }
}