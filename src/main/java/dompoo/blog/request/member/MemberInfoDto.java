package dompoo.blog.request.member;

import dompoo.blog.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoDto {

    private Long id;
    private String username;
    private int writingCount;
    private int commentCount;

    public MemberInfoDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.writingCount = member.getWriting().size();
        this.commentCount = member.getComment().size();
    }
}
