package dompoo.blog.repository;

import dompoo.blog.domain.Writing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingRepository extends JpaRepository<Writing, Long> {
}
