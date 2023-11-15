package dompoo.blog.domain;

import dompoo.blog.etc.ModifiedDate;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Comment extends ModifiedDate {

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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Reply> reply = new ArrayList<>();

    @Builder
    public Comment(String content, Member member, Writing writing) {
        this.content = content;
        setMember(member);
        setWriting(writing);

    }

    //연관관계 편의 메서드
    private void setMember(Member member) {
        this.member = member;
        member.getComment().add(this);
    }

    //연관관계 편의 메서드
    private void setWriting(Writing writing) {
        this.writing = writing;
        writing.getComment().add(this);
    }
}
