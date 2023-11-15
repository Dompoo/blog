package dompoo.blog.request.writing;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WritingVoteDto {

    private Long writingId;
    private Long memberId;

    public WritingVoteDto(Long writingId, Long memberId) {
        this.writingId = writingId;
        this.memberId = memberId;
    }
}
