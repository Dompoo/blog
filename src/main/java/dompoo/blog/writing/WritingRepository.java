package dompoo.blog.writing;

import dompoo.blog.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingRepository extends JpaRepository<Writing, Long> {

    Page<Writing> findBySubjectContaining(String subject);

    Page<Writing> findByMember(Member member);
}
