package dompoo.blog.member;

import dompoo.blog.member.Member;
import dompoo.blog.member.dto.MemberDto;
import dompoo.blog.member.dto.MemberSaveRequestDto;
import dompoo.blog.member.dto.MemberUpdataRequestDto;
import dompoo.blog.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 멤버 추가 메서드
     * 유저 이름이 중복될 경우 null을 리턴한다.
     */
    public MemberDto join(MemberSaveRequestDto dto) {

        Member member = new Member(dto.getUsername(), passwordEncoder.encode(dto.getPassword()));


        Member savedMember = memberRepository.save(member);
        return new MemberDto(savedMember);
    }

    /**
     * 멤버 전체 조회 메서드
     * pageable을 받아 Page<>로 리턴한다.
     */
    public Page<MemberDto> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
    }

    /**
     * 멤버 단건 조회 메서드
     * id로 조회한다.
     */
    public MemberDto findOne(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.map(MemberDto::new).orElse(null);
    }

    /**
     * 멤버 수정 메서드
     * id로 조회하고 dto로 받은 username과 password로 업데이트 한다.
     */
    public MemberDto update(
            Long memberId,
            MemberUpdataRequestDto dto
    ) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            return null;
        }
        Member member = findMember.get();
        member.setUsername(dto.getUsername());
        member.setPassword(dto.getPassword());

        return new MemberDto(member);
    }
}
