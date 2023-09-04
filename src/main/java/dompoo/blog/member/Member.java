package dompoo.blog.member;

import dompoo.blog.comment.Comment;
import dompoo.blog.writing.Writing;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "password"})
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Writing> writing;

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
