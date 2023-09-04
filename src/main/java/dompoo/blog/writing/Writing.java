package dompoo.blog.writing;

import dompoo.blog.comment.Comment;
import dompoo.blog.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Writing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "writing", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

    @ManyToOne
    private Member member;

    public Writing(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    //연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getWriting().add(this);
    }
}
