package dompoo.blog.repository.writing;

import dompoo.blog.domain.Writing;
import dompoo.blog.request.writing.WritingSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WritingRepositoryCustom {

    Page<Writing> search(WritingSearchCondition condition, Pageable pageable);
}
