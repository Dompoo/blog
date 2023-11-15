package dompoo.blog.etc;

import dompoo.blog.domain.Comment;
import dompoo.blog.domain.Member;
import dompoo.blog.domain.Writing;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Reply extends ModifiedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "writing_id")
    private Writing writing;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Reply(String content) {
        this.content = content;
    }

    //연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getReply().add(this);
    }

    //연관관계 편의 메서드
    public void setWriting(Writing writing) {
        this.writing = writing;
        writing.getReply().add(this);
    }

    //연관관계 편의 메서드
    public void setComment(Comment comment) {
        this.comment = comment;
        comment.getReply().add(this);
    }

}
