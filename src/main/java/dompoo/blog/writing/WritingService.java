package dompoo.blog.writing;

import dompoo.blog.member.Member;
import dompoo.blog.member.MemberRepository;
import dompoo.blog.writing.dto.WritingResponseDto;
import dompoo.blog.writing.dto.WritingSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
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
