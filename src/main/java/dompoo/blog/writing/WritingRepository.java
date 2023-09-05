package dompoo.blog.writing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingRepository extends JpaRepository<Writing, Long> {

    Page<Writing> findByTitleContaining(Pageable pageable, String title);

    Page<Writing> findByMember_Id(Pageable pageable, Long memberId);
}
