package dompoo.blog.writing;

import dompoo.blog.member.Member;
import dompoo.blog.member.MemberRepository;
import dompoo.blog.writing.dto.WritingResponseDto;
import dompoo.blog.writing.dto.WritingSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WritingService {

    private final WritingRepository writingRepository;
    private final MemberRepository memberRepository;

    /**
     * 글 추가 메서드
     * 멤버Id를 받아 연관관계 매핑 후 저장한다.
     */
    public WritingResponseDto saveWrite(WritingSaveDto dto, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElse(null);

        Writing writing = new Writing(dto.getSubject(), dto.getContent());
        writing.setMember(findMember);

        Writing saveWriting = writingRepository.save(writing);
        return new WritingResponseDto(saveWriting);
    }

    /**
     * 글 전체 조회 메서드
     * pageable을 받아 Page<>로 리턴한다.
     */
    public Page<WritingResponseDto> findAll(Pageable pageable) {

        Page<Writing> findWritings = writingRepository.findAll(pageable);
        return findWritings.map(WritingResponseDto::new);
    }

}
