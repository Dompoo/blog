package dompoo.blog.writing;

import dompoo.blog.writing.querydsl.WritingRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingRepository extends JpaRepository<Writing, Long>, WritingRepositoryCustom {

    Page<Writing> findByTitleContaining(Pageable pageable, String title);

    Page<Writing> findByMember_Id(Pageable pageable, Long memberId);
}
