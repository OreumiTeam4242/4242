package team.ftft.project4242.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.repository.*;
import team.ftft.project4242.service.file.AwsS3Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostTypeRepository postTypeRepository;
    @Mock
    private PostMajorRepository postMajorRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private TeamService teamService;
    @Mock
    private AwsS3Service awsS3Service;
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private ScrapRepository scrapRepository;

    @BeforeEach
    public void setUp() {
        // 각 테스트 메서드 실행 전 모든 mock 객체들 재설정 해주기
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        // todo save() 에서 Test 할 것 : 각 레포지토리에서 잘 가져오고 잘 저장하는지? + team, teamMember에 잘 저장되는지?
        // given - 데이터 준비
        Long memberId = 1L;
        PostRequestDto requestDto = new PostRequestDto();
        requestDto.setType_id(1L); // 모집 구분을 1로 설정 -> 1: 스터디
        requestDto.setMajor_id(1L); // 모집 분야를 1로 설정 -> 1: 수학

        Member member = new Member();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        PostType postType = new PostType();
        when(postTypeRepository.findById(requestDto.getType_id())).thenReturn(Optional.of(postType));

        PostMajor postMajor = new PostMajor();
        when(postMajorRepository.findById(requestDto.getMajor_id())).thenReturn(Optional.of(postMajor));

        MultipartFile file = mock(MultipartFile.class);
        when(awsS3Service.uploadFileBucket(file)).thenReturn("s3FilePath");

        Post savedPost = new Post();
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // when - 실행
        // postService의 save() 메서드 호출
        Post savedPostAndTeam = postService.save(requestDto, file, memberId);

        // then - 검증
        // 반환된 객체가 null이 아닌지 검증
        assertNotNull(savedPostAndTeam);

        // 각 메서드 호출이 예상대로 수행되었는지 검증
        verify(memberRepository).findById(memberId);
        verify(postTypeRepository).findById(requestDto.getType_id());
        verify(postMajorRepository).findById(requestDto.getMajor_id());
        verify(awsS3Service).uploadFileBucket(file);
        verify(postRepository).save(any(Post.class));
        verify(teamService).save(any(Team.class));
        verify(teamMemberRepository).save(any(TeamMember.class));
    }

    @Test
    void findAllAble() {
        // todo 메서드 호출 시 post1과 post2를 리스트 형태로 가져와야됨.
        // given
        Post post1 = new Post();
        Post post2 = new Post();
        List<Post> expectedPosts = List.of(post1, post2);

        when(postRepository.findAllAble()).thenReturn(expectedPosts);

        // when
        List<Post> Posts = postService.findAllAble();

        // then
        assertEquals(expectedPosts.size(), Posts.size());
        assertTrue(Posts.contains(post1));
        assertTrue(Posts.contains(post2));
    }

    @Test
    void findById() {
        // todo 주어진 id에 해당하는 게시글을 찾고, 조회수를 증가시킴.
        // given
        Long postId = 1L;
        Post expectedPosts = new Post();

        when(postRepository.findByIdAble(postId)).thenReturn(expectedPosts);

        // when
        Post resultPost = postService.findById(postId);

        // then
        verify(postRepository).findByIdAble(postId);
        verify(postRepository).incrementViewCountForActivePost(postId);
        assertEquals(expectedPosts, resultPost);
    }

    @Test
    void update() {
        // todo 글 수정.
        // given
        Long postId = 1L;
        String updatedContent = "Updated content";
        String oldContent = "Old content";
        MultipartFile newFile = new MockMultipartFile("newFile", "newFile.txt", "text/plain", "new file content".getBytes());
        String s3FilePath = "s3://bucket-name/newFile.txt";

        Post existingPost = Post.builder()
                .post_id(postId)
                .content(oldContent) // 이전 내용
                .file_url("s3://bucket-name/oldFile.txt") // 기존 파일 URL
                .build();

        when(postRepository.findByIdAble(postId)).thenReturn(existingPost);
        when(awsS3Service.uploadFileBucket(newFile)).thenReturn(s3FilePath);

        // when
        PostRequestDto requestDto = PostRequestDto.builder()
                .content(updatedContent)
                .build();
        Post updatedPost = postService.update(postId, requestDto, newFile);

        // then
        assertEquals(postId, updatedPost.getPost_id());
        assertEquals(updatedContent, updatedPost.getContent());
        assertEquals(s3FilePath, updatedPost.getFile_url());
    }

    @Test
    void disablePostById() {
        // todo 글 안보이게 하기.
        // given
        Long postId = 1L;

        // when
        postService.disablePostById(postId);

        // then
        verify(postRepository).deleteById(postId);
    }

    @Test
    void findTypePostAll() {
        // todo type_id로 모집구분별 전체 글 가져오기.
        // given
        Long typeId = 1L;
        List<Post> posts = new ArrayList<>();
        posts.add(new Post());
        posts.add(new Post()); // 2개의 예시 게시글 생성
        when(postRepository.findTypePostAll(typeId)).thenReturn(posts);

        // when
        List<Post> result = postService.findTypePostAll(typeId);

        // then
        assertEquals(posts.size(), result.size());
        assertEquals(posts, result);
    }

    @Test
    void findMajorPostAll() {
        // todo major_id로 모집분야별 전체 글 가져오기.
        // given
        Long majorId = 1L;
        List<Post> posts = new ArrayList<>();
        posts.add(new Post());
        posts.add(new Post());

        when(postRepository.findMajorPostAll(majorId)).thenReturn(posts);

        // when
        List<Post> result = postService.findMajorPostAll(majorId);

        // then
        assertEquals(posts.size(), result.size());
        assertEquals(posts, result);
    }

    @Test
    void findTop3PostsByViewCount() {
        // todo 조회수 top3 모집글 찾기.
        // given
        Post post1 = Post.builder()
                .post_id(1L)
                .build();

        Post post2 = Post.builder()
                .post_id(2L)
                .build();

        Post post3 = Post.builder()
                .post_id(3L)
                .build();

        List<Post> topPosts = Arrays.asList(post3, post1, post2);
        when(postRepository.findTop3PostsByViewCount()).thenReturn(topPosts);

        // when
        List<Post> result = postService.findTop3PostsByViewCount();

        // then
        // 반환된 목록의 크기는 3이어야 함
        assertEquals(3, result.size());
        // 반환된 목록의 순서는 viewCount 내림차순으로 정렬되어 있어야 함
        assertEquals(3L, result.get(0).getPost_id()); // 가장 높은 viewCount(post3)의 id는 3
        assertEquals(1L, result.get(1).getPost_id()); // 두 번째로 높은 viewCount(post1)의 id는 1
        assertEquals(2L, result.get(2).getPost_id()); // 세 번째로 높은 viewCount(post2)의 id는 2
    }

    @Test
    void findAllScrap() {
        // todo memberId로 Scrap 가져오기.
        // given
        Long memberId = 1L;

        Member member = Member.builder()
                .member_id(memberId)
                .build();

        Post post1 = Post.builder()
                .post_id(1L)
                .content("Post 1")
                .build();

        Post post2 = Post.builder()
                .post_id(2L)
                .content("Post 2")
                .build();

        Scrap scrap1 = Scrap.builder()
                .post(post1)
                .member(member)
                .build();

        Scrap scrap2 = Scrap.builder()
                .post(post2)
                .member(member)
                .build();

        List<Scrap> scrapList = List.of(scrap1, scrap2);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(scrapRepository.findAllByMember(member)).thenReturn(Optional.of(scrapList));

        // when
        List<PostResponseDto> result = postService.findAllScrap(memberId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getContent());
        assertEquals("Post 2", result.get(1).getContent());

        verify(memberRepository).findById(memberId);
        verify(scrapRepository).findAllByMember(member);
    }

    @Test
    void findOnGoingPostAll() {
        // todo 진행 중인 게시물 리스트 가져오기.
        // given
        Post post1 = Post.builder()
                .title("Post 1")
                .content("Content of Post 1")
                .use_yn(true)
                .build();

        Post post2 = Post.builder()
                .title("Post 2")
                .content("Content of Post 2")
                .use_yn(true)
                .build();

        List<Post> onGoingPosts = Arrays.asList(post1, post2);
        when(postRepository.findOnGoingPostAll()).thenReturn(onGoingPosts);

        // when
        List<Post> result = postService.findOnGoingPostAll();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getTitle());
        assertEquals("Post 2", result.get(1).getTitle());
    }

    @Test
    void findFinishPostAll() {
        // todo 완료된 게시물 리스트 가져오기.
        // given
        Post post1 = Post.builder()
                .title("Post 1")
                .content("Content of Post 1")
                .use_yn(false)
                .build();

        Post post2 = Post.builder()
                .title("Post 2")
                .content("Content of Post 2")
                .use_yn(false)
                .build();

        List<Post> finishPosts = Arrays.asList(post1, post2);
        when(postRepository.findFinishPostAll()).thenReturn(finishPosts);

        // when
        List<Post> result = postService.findFinishPostAll();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getTitle());
        assertEquals("Post 2", result.get(1).getTitle());
    }

    @Test
    void findMyPosts() {
        // todo member_id가 작성한 게시글 조회.
        // given
        Long memberId = 1L;
        Post post1 = Post.builder()
                .title("Post 1")
                .content("Content of Post 1")
                .build();

        Post post2 = Post.builder()
                .title("Post 2")
                .content("Content of Post 2")
                .build();

        List<Post> memberPosts = Arrays.asList(post1, post2);
        when(postRepository.findAllByMemberId(memberId)).thenReturn(memberPosts);

        // when
        List<Post> result = postService.findMyPosts(memberId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getTitle());
        assertEquals("Post 2", result.get(1).getTitle());
    }

    @Test
    void closePosts() {
        // when
        postService.closePosts();
    }
}
