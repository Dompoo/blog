package dompoo.blog.writing;

import dompoo.blog.comment.Comment;
import dompoo.blog.etc.ModifiedDate;
import dompoo.blog.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Writing extends ModifiedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "writing", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToMany
    private Set<Member> voteMembers = new HashSet<>();

    public Writing(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //연관관계 편의 메서드
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getWriting().remove(this);
        }
        this.member = member;
        member.getWriting().add(this);
    }
}
