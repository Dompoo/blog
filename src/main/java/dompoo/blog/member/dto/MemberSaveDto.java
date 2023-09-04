package dompoo.blog.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveDto {

    private String username;
    private String password;

    public MemberSaveDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
