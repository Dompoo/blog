package dompoo.blog.repository;

import dompoo.blog.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByWriting_Id(Pageable pageable, Long writingId);
}
