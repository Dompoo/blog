package dompoo.blog.service;

import dompoo.blog.domain.Member;
import dompoo.blog.exception.DataNotFoundException;
import dompoo.blog.repository.MemberRepository;
import dompoo.blog.request.member.MemberInfoDto;
import dompoo.blog.request.member.MemberSaveDto;
import dompoo.blog.request.member.MemberUpdateDto;
import dompoo.blog.response.MemberResponseDto;
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

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 멤버 추가 메서드
     * 유저 이름이 중복될 경우 null을 리턴한다.
     */
    public MemberResponseDto join(MemberSaveDto dto) {

        Member member = new Member(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword())
        );

        return new MemberResponseDto(repository.save(member));
    }

    /**
     * 멤버 전체 조회 메서드
     * pageable을 받아 Page<>로 리턴한다.
     */
    public Page<MemberResponseDto> findAll(Pageable pageable) {

        return repository.findAll(pageable)
                .map(MemberResponseDto::new);
    }

    /**
     * 멤버 단건 조회 메서드
     * id로 조회한다.
     */
    public MemberResponseDto findOne(Long memberId) {
        Optional<Member> findMember = repository.findById(memberId);
        return findMember.map(MemberResponseDto::new)
                .orElseThrow(() -> new DataNotFoundException("siteuser not found"));
    }

    public MemberResponseDto findByUsername(String username) {
        Optional<Member> findMember = repository.findByUsername(username);
        return findMember.map(MemberResponseDto::new)
                .orElseThrow(() -> new DataNotFoundException("siteuser not found"));
    }

    /**
     * 멤버 수정 메서드
     * id로 조회하고 dto로 받은 username과 password로 업데이트 한다.
     */
    public MemberResponseDto update(
            Long memberId,
            MemberUpdateDto dto
    ) {
        Optional<Member> findMember = repository.findById(memberId);
        if (findMember.isEmpty()) {
            return null;
        }

        Member member = findMember.get();
        member.setUsername(dto.getUsername());
        member.setPassword(dto.getPassword());
        return new MemberResponseDto(member);
    }

    /**
     * 내 정보 보기 메서드
     */
    public MemberInfoDto info(String username) {
        return new MemberInfoDto(repository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("Member Not Found")));
    }
}
