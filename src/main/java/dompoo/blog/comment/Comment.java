package dompoo.blog.comment;

import dompoo.blog.member.Member;
import dompoo.blog.writing.Writing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "writing_id")
    private Writing writing;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    //연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getComment().add(this);
    }

    //연관관계 편의 메서드
    public void setWriting(Writing writing) {
        this.writing = writing;
        writing.getComment().add(this);
    }
}
