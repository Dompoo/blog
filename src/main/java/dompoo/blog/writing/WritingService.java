package dompoo.blog.writing;

import dompoo.blog.exception.DataNotFoundException;
import dompoo.blog.member.Member;
import dompoo.blog.member.MemberRepository;
import dompoo.blog.writing.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WritingService {

    private final WritingRepository writingRepository;
    private final MemberRepository memberRepository;

    /**
     * 글 추가 메서드
     * title, content, memberId를 받아 연관관계 매핑 후 저장한다.
     */
    public WritingResponseDto saveWrite(WritingSaveDto dto) {

        Member findMember = memberRepository.findById(dto.getMemberId()).orElse(null);

        Writing writing = new Writing(dto.getSubject(), dto.getTitle());
        writing.setMember(findMember);

        Writing saveWriting = writingRepository.save(writing);
        return new WritingResponseDto(saveWriting);
    }

    /**
     * 글 전체 조회 메서드
     * Pageable을 받아 Page<>로 리턴한다.
     */
    public Page<WritingResponseDto> findAll(Pageable pageable) {
        Page<Writing> findWritings = writingRepository.findAll(pageable);
        return findWritings.map(WritingResponseDto::new);
    }

    /**
     * 글 옵션 조회 메서드
     * condition으로 필터 검색
     */
    public Page<WritingResponseDto> findByCond(WritingSearchCondition condition, Pageable pageable) {

        return writingRepository.search(condition, pageable).map(WritingResponseDto::new);
    }

    /**
     * 글 Id 조회 메서드
     * writingId를 받아 Optional로 리턴한다.
     */
    public WritingResponseDto findOne(Long writingId) {

        return new WritingResponseDto(writingRepository.findById(writingId)
                .orElseThrow(() -> new DataNotFoundException("Writing Not Found")));
    }

    /**
     * 글 제목 조회 메서드
     * Pageable, title을 받아 Page<>로 리턴한다.
     */
    public Page<WritingResponseDto> findBySubject(Pageable pageable, String title) {

        Page<Writing> findWritings = writingRepository.findByTitleContaining(pageable, title);
        return findWritings.map(WritingResponseDto::new);
    }

    /**
     * 글 작성자 조회 메서드
     * Pageable, username을 받아 Page<>로 리턴한다.
     */
    public Page<WritingResponseDto> findByUsername(Pageable pageable, String username) {

        Optional<Member> findMember = memberRepository.findByUsername(username);

        if (findMember.isEmpty()) {
            log.info("findMember Empty!");
            return null;
        }

        Page<Writing> findWritings = writingRepository.findByMember_Id(pageable, findMember.get().getId());
        return findWritings.map(WritingResponseDto::new);
    }

    /**
     * 글 수정 메서드
     * id, title, content를 받아 title, content를 수정한다.
     */
    public WritingResponseDto updateWriting(WritingUpdateDto dto) {
        Optional<Writing> findWriting = writingRepository.findById(dto.getWritingId());

        if (findWriting.isEmpty()) {
            return null;
        }

        Writing writing = findWriting.get();
        writing.setTitle(dto.getTitle());
        writing.setContent(dto.getContent());

        return new WritingResponseDto(writing);
    }

    /**
     * 글 삭제 메서드
     * id를 받아 삭제한다.
     */
    public void deleteWriting(Long writingId) {
        writingRepository.deleteById(writingId);
    }

    public void voteWriting(WritingVoteDto dto) {
        Writing findWriting = writingRepository.findById(dto.getWritingId()).orElseThrow(() -> new DataNotFoundException("Writing Not Found"));
        Member findMember = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new DataNotFoundException("Member Not Found"));

        findWriting.getVoteMembers().add(findMember);
    }

}
