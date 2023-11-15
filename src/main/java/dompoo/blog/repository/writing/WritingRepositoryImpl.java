package dompoo.blog.repository.writing;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dompoo.blog.domain.QWriting;
import dompoo.blog.domain.Writing;
import dompoo.blog.request.writing.WritingSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static dompoo.blog.domain.QWriting.writing;
import static org.springframework.util.StringUtils.hasLength;

@RequiredArgsConstructor
public class WritingRepositoryImpl implements WritingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Writing> search(WritingSearchCondition condition, Pageable pageable) {
        List<Writing> content = queryFactory
                .select(new QWriting(writing))
                .from(writing)
                .where(usernameEq(condition.getUsername()),
                        writingTitleEQ(condition.getWritingTitle()),
                        writingContentContain(condition.getWritingContent()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(writing.modifiedDate.desc())
                .fetch();

        JPAQuery<Writing> countQuery = queryFactory
                .select(writing)
                .from(writing)
                .where(usernameEq(condition.getUsername()),
                        writingTitleEQ(condition.getWritingTitle()),
                        writingContentContain(condition.getWritingContent()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression usernameEq(String username) {
        return !hasLength(username) ? null : writing.member.username.eq(username);
    }

    private BooleanExpression writingTitleEQ(String title) {
        return !hasLength(title) ? null : writing.title.eq(title);
    }

    private BooleanExpression writingContentContain(String content) {
        return !hasLength(content) ? null : writing.content.contains(content);
    }
}
