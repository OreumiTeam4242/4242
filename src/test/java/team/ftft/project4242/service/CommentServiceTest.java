package team.ftft.project4242.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.ftft.project4242.domain.Comment;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.CommentRequestDto;
import team.ftft.project4242.dto.CommentResponseDto;
import team.ftft.project4242.repository.CommentRepository;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.PostRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveComment() {
        CommentRequestDto requestDto = CommentRequestDto.builder().content("testContent").build();

        Post post = Post.builder().post_id(1L).build();
        Member member = Member.builder().member_id(1L).build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Comment savedComment = Comment.builder().content("testContent").build();
        when(commentRepository.save(any())).thenReturn(savedComment);

        CommentResponseDto result = commentService.saveComment(requestDto, 1L, 1L);

        assertThat(result.getContent()).isEqualTo("testContent");
    }

    @Test
    public void testDeleteComment() {
        // Given
        Member member = Member.builder()
                .member_id(1L)
                .nickname("testNickname")
                .build();

        Comment comment = Comment.builder()
                .comment_id(1L)
                .member(member)
                .build();

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        // When
        CommentResponseDto result = commentService.deleteComment(1L);

        // Then
        assertThat(result.getCommentId()).isEqualTo(1L);
        assertThat(result.getNickname()).isEqualTo("testNickname");

        verify(commentRepository, times(1)).findById(1L);
    }

}
