package dompoo.blog.writing.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WritingVoteDto {

    private Long writingId;
    private Long memberId;

    public WritingVoteDto(Long writingId, Long memberId) {
        this.writingId = writingId;
        this.memberId = memberId;
    }
}
