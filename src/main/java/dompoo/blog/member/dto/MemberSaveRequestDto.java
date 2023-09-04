package dompoo.blog.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String username;
    private String password;

    public MemberSaveRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
