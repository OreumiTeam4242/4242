package team.ftft.project4242.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Notify;
import team.ftft.project4242.dto.NotifyRequestDto;
import team.ftft.project4242.dto.NotifyResponseDto;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.NotifyRepository;
import team.ftft.project4242.service.file.AwsS3Service;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class NotifyServiceTest {

    @InjectMocks
    private NotifyService notifyService;

    @Mock
    private NotifyRepository notifyRepository;

    @Mock
    private AwsS3Service awsS3Service;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveNotify() {

        Member postMember = Member.builder().member_id(1L).build();

        Member notifyMember = Member.builder().nickname("testMemberName").build();

        when(memberRepository.findById(1L)).thenReturn(Optional.of(postMember));
        when(memberRepository.findByNickname("testMemberName")).thenReturn(Optional.of(notifyMember));
        when(awsS3Service.uploadFileBucket(any())).thenReturn("testFilePath");

        Notify savedNotify = Notify.builder().build();
        when(notifyRepository.save(any())).thenReturn(savedNotify);

        NotifyRequestDto requestDto = NotifyRequestDto.builder().notifyMemberName("testMemberName").build();

        Notify result = notifyService.saveNotify(requestDto, null, 1L);

        assertThat(result).isEqualTo(savedNotify);
    }

    @Test
    public void testFindAllNotify() {
        Notify notify = Notify.builder().title("testTitle").content("testContent").build();

        when(notifyRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(notify)));

        Page<NotifyResponseDto> result = notifyService.findAllNotify(Pageable.unpaged());

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("testTitle");
    }

    @Test
    public void testFindById() {
        Notify notify = Notify.builder().notify_id(1L).build();
        when(notifyRepository.findById(1L)).thenReturn(Optional.of(notify));

        Notify result = notifyService.findById(1L);

        assertThat(result.getNotify_id()).isEqualTo(1L);
    }

    @Test
    public void testIsMemberExists() {
        Member member = Member.builder().nickname("testMember").build();

        when(memberRepository.findByNickname("testMember")).thenReturn(Optional.of(member));

        boolean result = notifyService.isMemberExists("testMember");

        assertThat(result).isTrue();
    }
}
