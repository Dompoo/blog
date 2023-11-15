package dompoo.blog.domain;

import com.querydsl.core.annotations.QueryProjection;
import dompoo.blog.etc.ModifiedDate;
import jakarta.persistence.*;
import lombok.Builder;
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

    @OneToMany(mappedBy = "writing", cascade = CascadeType.REMOVE)
    private List<Reply> reply = new ArrayList<>();

    @ManyToMany
    private Set<Member> voteMembers = new HashSet<>();

    public Writing(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @QueryProjection
    @Builder
    public Writing(Long id, String title, String content, LocalDateTime modifiedDate, List<Comment> comment, Set<Member> voteMembers, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modifiedDate = modifiedDate;
        this.comment = comment;
        this.voteMembers = voteMembers;
        setMember(member);
    }

    //연관관계 편의 메서드
    private void setMember(Member member) {
        if (this.member != null) {
            this.member.getWriting().remove(this);
        }
        this.member = member;
        member.getWriting().add(this);
    }
}
