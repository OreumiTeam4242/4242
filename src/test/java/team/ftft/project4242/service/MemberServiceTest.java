package team.ftft.project4242.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Role;
import team.ftft.project4242.dto.MemberRequestDto;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.service.file.AwsS3Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private AwsS3Service awsS3Service;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterMember() {
        // Given
        MemberRequestDto request = new MemberRequestDto();
        request.setEmail("test@example.com");
        request.setNickname("testUser");
        request.setPassword("testPassword");

        Member mockMember = Member.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password("encodedPassword")
                .img_url("https://eunji-web-bucket.s3.ap-northeast-2.amazonaws.com/4cb67e60-0bbb-4701-b248-2460e0082fab.jpg")
                .use_yn(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(Role.ROLE_NEW_BIE)
                .build();

        when(memberRepository.save(any(Member.class))).thenReturn(mockMember);

        // When
        Member result = memberService.registerMember(request);

        // Then
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("testUser", result.getNickname());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("https://eunji-web-bucket.s3.ap-northeast-2.amazonaws.com/4cb67e60-0bbb-4701-b248-2460e0082fab.jpg", result.getImg_url());
        assertTrue(result.isUse_yn());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertEquals(Role.ROLE_NEW_BIE, result.getRole());
    }

    @Test
    public void testIsEmailExists() {
        String email = "test@test.com";
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(new Member()));

        boolean result = memberService.isEmailExists(email);

        assertTrue(result);
    }

    @Test
    public void testIsNicknameExists() {
        String nickname = "testNickname";
        when(memberRepository.findByNickname(nickname)).thenReturn(Optional.of(new Member()));

        boolean result = memberService.isNicknameExists(nickname);

        assertTrue(result);
    }

    @Test
    public void testFindById() {
        // Given
        Long memberId = 1L;
        Member mockMember = createMockMember(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

        // When
        MemberResponseDto result = memberService.findById(memberId);

        // Then
        assertNotNull(result);
        assertEquals(mockMember.getEmail(), result.getEmail());
        assertEquals(mockMember.getNickname(), result.getNickname());
    }

    @Test
    public void testUpdate() {
        // Given
        Long memberId = 1L;
        MemberRequestDto request = new MemberRequestDto();
        request.setNickname("updatedNickname");

        Member mockMember = createMockMember(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));
        when(awsS3Service.uploadImageBucket(any(MultipartFile.class))).thenReturn("s3FilePath");

        // When
        MemberResponseDto result = memberService.update(memberId, request, mock(MultipartFile.class));

        // Then
        assertNotNull(result);
        assertEquals(request.getNickname(), result.getNickname());
    }

    @Test
    public void testDisabled() {
        // Given
        Long memberId = 1L;
        Member mockMember = createMockMember(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

        // When
        MemberResponseDto result = memberService.disabled(memberId);

        // Then
        assertNotNull(result);
    }

    @Test
    public void testEnable() {
        // Given
        Long memberId = 1L;
        Member mockMember = createMockMember(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

        // When
        MemberResponseDto result = memberService.enable(memberId);

        // Then
        assertNotNull(result);
    }

    @Test
    public void testDelete() {
        // Given
        Long memberId = 1L;
        Member mockMember = createMockMember(memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

        // When
        MemberResponseDto result = memberService.delete(memberId);

        // Then
        assertNotNull(result);
        verify(memberRepository, times(1)).delete(mockMember);
    }

    private Member createMockMember(Long memberId) {
        return Member.builder()
                .member_id(memberId)
                .email("test@example.com")
                .password("encodedPassword")
                .nickname("testUser")
                .img_url("https://eunji-web-bucket.s3.ap-northeast-2.amazonaws.com/4cb67e60-0bbb-4701-b248-2460e0082fab.jpg")
                .use_yn(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(Role.ROLE_NEW_BIE)
                .build();
    }

    @Test
    public void testCheckAndUnsuspendMembers() {
        // 해당 메서드의 구현을 직접 호출하여 테스트
        memberService.checkAndUnsuspendMembers();
    }
}
