package dompoo.blog.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateDto {

    private String username;
    private String password;

    public MemberUpdateDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
