package team.ftft.project4242.service;

import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Comment;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.CommentRequestDto;
import team.ftft.project4242.dto.CommentResponseDto;
import team.ftft.project4242.repository.CommentRepository;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.PostRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }
    public CommentResponseDto saveComment(CommentRequestDto request,Long postId,Long memberId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("post doesn't exist"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member doesn't exist"));

        Comment comment = request.toEntity(post,member);
        commentRepository.save(comment);
        return comment.toResponse();
    }

    public CommentResponseDto deleteComment(Long commentId){
        // TODO : 삭제가 아닌 use_yn value 변경
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("comment doesn't exist"));
        commentRepository.delete(comment);
        return comment.toResponse();
    }
}
