package team.ftft.project4242.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Scrap;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.repository.ScrapRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ScrapServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private ScrapRepository scrapRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks // 실제로 테스트할 서비스 객체
    private ScrapService scrapService;

    @Test
    @DisplayName("스크랩 등록")
    void testUpdateScrap_CreateNewScrap() {
        Long postId = 1L;
        Long memberId = 1L;

        Post post = new Post();
        Member member = new Member();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(scrapRepository.findByPostAndMember(post, member)).thenReturn(Optional.empty());

        scrapService.updateScrap(postId, memberId);

        verify(scrapRepository, times(1)).save(any(Scrap.class));
    }

    @Test
    @DisplayName("스크랩 해제")
    void testUpdateScrap_DeleteExistingScrap() {
        Long postId = 1L;
        Long memberId = 1L;

        Post post = new Post();
        Member member = new Member();
        Scrap scrap = new Scrap(post, member);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(scrapRepository.findByPostAndMember(post, member)).thenReturn(Optional.of(scrap));

        scrapService.updateScrap(postId, memberId);

        verify(scrapRepository, times(1)).delete(scrap);
    }

    @Test
    @DisplayName("스크랩이 되어있을 때 true 반환 확인")
    void testIsScrapped_ReturnsTrue() {
        Long postId = 1L;
        Long memberId = 1L;

        when(scrapRepository.existsByPostIdAndMemberId(postId, memberId)).thenReturn(new Scrap());

        assertTrue(scrapService.isScrapped(postId, memberId));
    }

    @Test
    @DisplayName("스크랩이 되어 있지 않을 때 false 반환 확인")
    void testIsScrapped_ReturnsFalse() {
        Long postId = 1L;
        Long memberId = 1L;

        when(scrapRepository.existsByPostIdAndMemberId(postId, memberId)).thenReturn(null);

        assertFalse(scrapService.isScrapped(postId, memberId));
    }
}
