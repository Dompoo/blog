package dompoo.blog.comment;

import dompoo.blog.member.Member;
import dompoo.blog.writing.Writing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
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
    private Writing writing;

    @ManyToOne
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
