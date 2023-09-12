package dompoo.blog.writing.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dompoo.blog.writing.QWriting;
import dompoo.blog.writing.Writing;
import dompoo.blog.writing.dto.WritingSearchCondition;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static dompoo.blog.writing.QWriting.writing;
import static org.springframework.util.StringUtils.hasLength;


public class WritingRepositoryImpl implements WritingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public WritingRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Writing> search(WritingSearchCondition condition, Pageable pageable) {
        List<Writing> content = queryFactory
                .select(new QWriting(writing))
                .from(writing)
                .where(usernameEq(condition.getUsername()),
                        writingTitleEQ(condition.getWritingTitle()),
                        writingContentEq(condition.getWritingContent()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(writing.modifiedDate.desc())
                .fetch();

        JPAQuery<Writing> countQuery = queryFactory
                .select(writing)
                .from(writing)
                .where(usernameEq(condition.getUsername()),
                        writingTitleEQ(condition.getWritingTitle()),
                        writingContentEq(condition.getWritingContent()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression usernameEq(String username) {
        return !hasLength(username) ? null : writing.member.username.eq(username);
    }

    private BooleanExpression writingTitleEQ(String title) {
        return !hasLength(title) ? null : writing.title.eq(title);
    }

    private BooleanExpression writingContentEq(String content) {
        return !hasLength(content) ? null : writing.content.eq(content);
    }
}
