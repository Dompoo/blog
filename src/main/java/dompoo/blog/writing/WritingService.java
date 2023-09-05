package dompoo.blog.writing;

import dompoo.blog.member.Member;
import dompoo.blog.member.MemberRepository;
import dompoo.blog.writing.dto.WritingResponseDto;
import dompoo.blog.writing.dto.WritingSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WritingService {

    private final WritingRepository writingRepository;
    private final MemberRepository memberRepository;

    public WritingResponseDto saveWrite(WritingSaveDto dto, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElse(null);

        Writing writing = new Writing(dto.getSubject(), dto.getContent());
        writing.setMember(findMember);

        Writing saveWriting = writingRepository.save(writing);
        return new WritingResponseDto(saveWriting);
    }

}
