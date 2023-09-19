package dompoo.blog.comment;

import dompoo.blog.comment.dto.CommentResponseDto;
import dompoo.blog.comment.dto.CommentSaveDto;
import dompoo.blog.comment.dto.CommentUpdateDto;
import dompoo.blog.exception.DataNotFoundException;
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
     * 댓글 추가
     */
    public CommentResponseDto saveComment(CommentSaveDto dto) {

        Member findMember = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new DataNotFoundException("Member Not Found"));
        Writing findWriting = writingRepository.findById(dto.getWritingId()).orElseThrow(() -> new DataNotFoundException("Member Not Found"));

        Comment comment = new Comment(dto.getContent());
        comment.setMember(findMember);
        comment.setWriting(findWriting);

        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);
    }

    /**
     * 댓글 달린 글로 검색
     */
    public Page<CommentResponseDto> findByWritingId(Pageable pageable, Long writingId) {

        Page<Comment> findComment = commentRepository.findByWriting_Id(pageable, writingId);
        return findComment.map(CommentResponseDto::new);
    }

    /**
     * Id로 댓글 검색
     */
    public CommentResponseDto findById(Long commentId) {

        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new DataNotFoundException("Comment Not Found"));

        return new CommentResponseDto(findComment);
    }

    /**
     * 댓글 수정
     */
    public CommentResponseDto updateComment(CommentUpdateDto dto) {

        Comment findComment = commentRepository.findById(dto.getCommentId()).orElseThrow(() -> new DataNotFoundException("Comment Not Found"));
        findComment.setContent(dto.getContent());

        return new CommentResponseDto(findComment);
    }

    /**
     * 댓글 삭제
     */
    public void deleteComment(Long commentId) {

        commentRepository.deleteById(commentId);
    }

}
