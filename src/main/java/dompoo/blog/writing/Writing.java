package dompoo.blog.writing;

import dompoo.blog.comment.Comment;
import dompoo.blog.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Writing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "writing", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

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
