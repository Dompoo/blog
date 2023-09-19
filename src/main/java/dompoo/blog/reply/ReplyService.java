package dompoo.blog.reply;

import dompoo.blog.comment.Comment;
import dompoo.blog.comment.CommentRepository;
import dompoo.blog.exception.DataNotFoundException;
import dompoo.blog.member.Member;
import dompoo.blog.member.MemberRepository;
import dompoo.blog.reply.dto.ReplyResponseDto;
import dompoo.blog.reply.dto.ReplySaveDto;
import dompoo.blog.writing.Writing;
import dompoo.blog.writing.WritingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final MemberRepository memberRepository;
    private final WritingRepository writingRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    public ReplyResponseDto addReply(ReplySaveDto dto) {

        Member findMember = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new DataNotFoundException("Member Not Found"));
        Writing findWriting = writingRepository.findById(dto.getWritingId()).orElseThrow(() -> new DataNotFoundException("Writing Not Found"));
        Comment findComment = commentRepository.findById(dto.getCommentId()).orElseThrow(() -> new DataNotFoundException("Comment Not Found"));

        Reply reply = new Reply(dto.getContent());
        reply.setMember(findMember);
        reply.setWriting(findWriting);
        reply.setComment(findComment);

        return new ReplyResponseDto(replyRepository.save(reply));
    }
}
