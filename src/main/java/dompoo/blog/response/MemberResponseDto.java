package dompoo.blog.response;

import dompoo.blog.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String username;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
