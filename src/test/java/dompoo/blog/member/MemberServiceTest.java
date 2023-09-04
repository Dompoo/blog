package dompoo.blog.member;

import dompoo.blog.member.dto.MemberDto;
import dompoo.blog.member.dto.MemberSaveRequestDto;
import dompoo.blog.member.dto.MemberUpdataRequestDto;
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

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void join() {
        MemberSaveRequestDto member1 = new MemberSaveRequestDto("member1", "password1");
        MemberSaveRequestDto member2 = new MemberSaveRequestDto("member2", "password2");
        memberService.join(member1);
        memberService.join(member2);

        assertThat(memberRepository.count()).isEqualTo(2);
    }

    @Test
    void findAll() {
        MemberSaveRequestDto member1 = new MemberSaveRequestDto("member1", "password1");
        MemberSaveRequestDto member2 = new MemberSaveRequestDto("member2", "password2");
        memberService.join(member1);
        memberService.join(member2);

        Page<MemberDto> findMembers = memberService.findAll(PageRequest.of(0, 10));

        assertThat(findMembers.getTotalElements()).isEqualTo(2);
    }

    @Test
    void findOne() {
        MemberSaveRequestDto member = new MemberSaveRequestDto("member1", "password1");
        MemberDto saveMember = memberService.join(member);

        MemberDto findMember = memberService.findOne(saveMember.getId());

        assertThat(findMember.getId()).isEqualTo(saveMember.getId());
    }

    @Test
    void update() {
        MemberSaveRequestDto member = new MemberSaveRequestDto("member1", "password1");
        MemberDto saveMember = memberService.join(member);

        memberService.update(saveMember.getId(), new MemberUpdataRequestDto("updateName", "updatePassword"));
        Member findMember = memberRepository.findById(saveMember.getId()).get();
        assertThat(findMember.getUsername()).isEqualTo("updateName");
        assertThat(findMember.getPassword()).isEqualTo("updatePassword");
    }
}