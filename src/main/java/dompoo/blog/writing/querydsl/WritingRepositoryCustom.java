package dompoo.blog.writing.querydsl;

import dompoo.blog.writing.Writing;
import dompoo.blog.writing.dto.WritingSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WritingRepositoryCustom {

    Page<Writing> search(WritingSearchCondition condition, Pageable pageable);
}
