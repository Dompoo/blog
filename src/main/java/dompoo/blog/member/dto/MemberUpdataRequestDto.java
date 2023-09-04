package dompoo.blog.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdataRequestDto {

    private String username;
    private String password;

    public MemberUpdataRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
