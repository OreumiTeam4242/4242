package team.ftft.project4242.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.service.PostService;
import team.ftft.project4242.service.TeamService;

import java.time.LocalDate;
import java.util.List;

@Tag(name="스터디/프로젝트 모집글 CRUD")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "모집글 생성", description = "스터디/프로젝트 모집글을 생성")
    @PostMapping("/api/post")
    public ResponseEntity<PostResponseDto> addPost(@RequestPart PostRequestDto request
                                                   , @RequestPart(value = "file", required = false) MultipartFile file
                                                    , @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getMemberId();
        Post post = postService.save(request,file,memberId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(post.toResponse());
    }

    @Operation(summary = "모집글 전체 조회", description = "메인페이지에서의 스터디/프로젝트 모집글 전체 조회")
    @GetMapping ("/api/posts")
    public ResponseEntity<List<PostResponseDto>> showPost() {
        List<Post> postList = postService.findAllAble();
        if (postList == null || postList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 빈 목록일 경우 noContent 상태 코드 반환
        }
        List<PostResponseDto> responseList = postList.stream()
                .map(PostResponseDto::new)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "모집글 수정", description = "스터디/프로젝트 모집글 수정 - 모집글 내용을 수정할 수 있다.")
    @PutMapping("/api/post/{post_id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long post_id,
                                           @RequestPart PostRequestDto request,
                                           @RequestPart(value="file",required = false) MultipartFile file) {
        Post updatedPost = postService.update(post_id, request,file);
        return ResponseEntity.ok(updatedPost.toResponse());
    }

    @Operation(summary = "모집글 삭제", description = "스터디/프로젝트 모집글에 대한 삭제")
    @Transactional
    @DeleteMapping("/api/post/{post_id}/disable")
    public ResponseEntity<Void> disablePostById(@PathVariable Long post_id) {
        postService.disablePostById(post_id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모집글 구분에 대한 필터 조회", description = "메인페이지에서 스터디/프로젝트 모집글을 모집 구분에 따라 필터 조회")
    @GetMapping("/api/posts/type/{type_id}")
    public ResponseEntity<List<PostResponseDto>> showTypePost(@PathVariable Long type_id) {
        List<Post> typePostList = postService.findTypePostAll(type_id);
        if (typePostList == null || typePostList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 빈 목록일 경우 noContent 상태 코드 반환
        }
        List<PostResponseDto> responseList = typePostList.stream()
                .map(PostResponseDto::new)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "모집글 분야에 대한 필터 조회", description = "메인페이지에서 스터디/프로젝트 모집글을 모집 분야에 따라 필터 조회")
    @GetMapping("/api/posts/major/{major_id}")
    public ResponseEntity<List<PostResponseDto>> showMajorPost(@PathVariable Long major_id) {
        List<Post> majorPostList = postService.findMajorPostAll(major_id);
        if (majorPostList == null || majorPostList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 빈 목록일 경우 noContent 상태 코드 반환
        }
        List<PostResponseDto> responseList = majorPostList.stream()
                .map(PostResponseDto::new)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "HOT 모집글 조회", description = "메인페이지에서  HOT 스터디/프로젝트 모집글 - 조회수가 가장 많은 3개의 모집글 조회 ")
    @GetMapping("/api/posts/hot")
    public ResponseEntity<List<PostResponseDto>> showHotPost() {
        List<Post> hotPostList = postService.findTop3PostsByViewCount();
        if (hotPostList == null || hotPostList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 빈 목록일 경우 noContent 상태 코드 반환
        }
        List<PostResponseDto> responseList = hotPostList.stream()
                .map(PostResponseDto::new)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    // 매일 자정에 자동으로 is_closed 변경
    @Operation(summary = "모집글 마감", description = "매일 자정 자동으로 모집글 마감 기한 check 후 마감")
    @Scheduled(cron = "0 0 0 * * ?")
    public void closePosts() {
        postService.closePosts();
    }
}
