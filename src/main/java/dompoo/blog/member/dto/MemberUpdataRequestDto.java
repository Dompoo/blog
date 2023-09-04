package dompoo.blog.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdataRequestDto {

    private String username;
    private String password;
}
