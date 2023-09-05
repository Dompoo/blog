package dompoo.blog.writing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingRepository extends JpaRepository<Writing, Long> {

    Page<Writing> findBySubjectContaining(Pageable pageable, String subject);

    Page<Writing> findByMember_Id(Pageable pageable, Long memberId);
}
