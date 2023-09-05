package dompoo.blog.comment;

import dompoo.blog.comment.dto.CommentResponseDto;
import dompoo.blog.comment.dto.CommentSaveDto;
import dompoo.blog.member.Member;
import dompoo.blog.member.MemberRepository;
import dompoo.blog.writing.Writing;
import dompoo.blog.writing.WritingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final WritingRepository writingRepository;
    private final MemberRepository memberRepository;

    /**
     * 댓글 추가 메서드
     */
    public CommentResponseDto saveComment(CommentSaveDto dto) {

        Member findMember = memberRepository.findById(dto.getMemberId()).orElse(null);
        Writing findWriting = writingRepository.findById(dto.getWritingId()).orElse(null);

        Comment comment = new Comment(dto.getContent());
        comment.setMember(findMember);
        comment.setWriting(findWriting);

        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);
    }

    /**
     * 댓글 달린 글로 검색 메서드
     */
    public Page<CommentResponseDto> findByWritingId(Pageable pageable, Long writingId) {

        Page<Comment> findComment = commentRepository.findByWriting_Id(pageable, writingId);
        return findComment.map(CommentResponseDto::new);
    }

}
