package dompoo.blog.domain;

import dompoo.blog.etc.ModifiedDate;
import jakarta.persistence.*;
import lombok.*;
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

    @Builder
    public Reply(String content, Member member, Writing writing, Comment comment) {
        this.content = content;
        setMember(member);
        setWriting(writing);
        setComment(comment);
    }

    //연관관계 편의 메서드
    private void setMember(Member member) {
        this.member = member;
        member.getReply().add(this);
    }

    //연관관계 편의 메서드
    private void setWriting(Writing writing) {
        this.writing = writing;
        writing.getReply().add(this);
    }

    //연관관계 편의 메서드
    private void setComment(Comment comment) {
        this.comment = comment;
        comment.getReply().add(this);
    }

}
