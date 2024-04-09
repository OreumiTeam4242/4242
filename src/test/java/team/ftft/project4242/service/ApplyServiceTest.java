package team.ftft.project4242.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.ApplyRequestDto;
import team.ftft.project4242.dto.ApplyResponseDto;
import team.ftft.project4242.repository.ApplyRepository;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.service.file.AwsS3Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class ApplyServiceTest {

    @InjectMocks
    private ApplyService applyService;

    @Mock
    private ApplyRepository applyRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private AwsS3Service awsS3Service;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveApply() {
        ApplyRequestDto requestDto = ApplyRequestDto.builder()
                .title("testTitle")
                .content("testContent")
                .available_time("testTime")
                .available_day("testDay")
                .build();

        Post post = Post.builder().post_id(1L).build();
        Member member = Member.builder().member_id(1L).build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Apply savedApply = Apply.builder()
                .title("testTitle")
                .content("testContent")
                .available_time("testTime")
                .available_day("testDay")
                .file_url("testFileUrl")
                .member(member)
                .post(post)
                .build();

        when(awsS3Service.uploadFileBucket(any())).thenReturn("testFileUrl");
        when(applyRepository.save(any())).thenReturn(savedApply);

        Apply result = applyService.saveApply(requestDto, 1L, null, 1L);

        assertThat(result.getTitle()).isEqualTo("testTitle");
        assertThat(result.getContent()).isEqualTo("testContent");
        assertThat(result.getAvailable_time()).isEqualTo("testTime");
        assertThat(result.getAvailable_day()).isEqualTo("testDay");
        assertThat(result.getFile_url()).isEqualTo("testFileUrl");
    }

    @Test
    void testFindAllApply() {
        // Given
        Long postId = 1L;
        Pageable pageable = Pageable.unpaged();

        Apply apply1 = Apply.builder()
                .apply_id(1L)
                .title("Test Apply 1")
                .build();

        Apply apply2 = Apply.builder()
                .apply_id(2L)
                .title("Test Apply 2")
                .build();

        List<Apply> applyList = Arrays.asList(apply1, apply2);
        Page<Apply> applies = new PageImpl<>(applyList);

        when(applyRepository.findAllByPostId(postId, pageable)).thenReturn(applies);

        // When
        Page<ApplyResponseDto> result = applyService.findAllApply(postId, pageable);

        // Then
        assertEquals(2, result.getContent().size());
        assertEquals(apply1.getApply_id(), result.getContent().get(0).getId());
        assertEquals(apply1.getTitle(), result.getContent().get(0).getTitle());
        assertEquals(apply2.getApply_id(), result.getContent().get(1).getId());
        assertEquals(apply2.getTitle(), result.getContent().get(1).getTitle());

        verify(applyRepository, times(1)).findAllByPostId(postId, pageable);
    }

    @Test
    public void testFindById() {
        Apply apply = Apply.builder().apply_id(1L).build();

        when(applyRepository.findById(1L)).thenReturn(Optional.of(apply));

        Apply result = applyService.findById(1L);

        assertThat(result.getApply_id()).isEqualTo(1L);
    }
}
