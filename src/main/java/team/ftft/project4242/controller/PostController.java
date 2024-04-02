package team.ftft.project4242.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.service.PostService;
import team.ftft.project4242.service.TeamService;
import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/post")
    public ResponseEntity<PostResponseDto> addPost(@RequestPart PostRequestDto request
                                                   , @RequestPart(value = "file", required = false) MultipartFile file
                                                    , @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getMemberId();
        Post post = postService.save(request,file,memberId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(post.toResponse());
    }

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

    @PutMapping("/api/post/{post_id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long post_id,
                                           @RequestPart PostRequestDto request,
                                           @RequestPart(value="file",required = false) MultipartFile file) {
        Post updatedPost = postService.update(post_id, request,file);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/api/post/{post_id}")
    public ResponseEntity<PostResponseDto> showPostById(@PathVariable Long post_id) {
        Post post = postService.findById(post_id);
        post.setViewCount(post.getViewCount() + 1);
        return ResponseEntity.ok(post.toResponse());
    }

    @Transactional
    @DeleteMapping("/api/post/{post_id}/disable")
    public ResponseEntity<Void> disablePostById(@PathVariable Long post_id) {
        postService.disablePostById(post_id);
        return ResponseEntity.ok().build();
    }

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

    @GetMapping("/api/posts/scrap")
    public ResponseEntity<List<PostResponseDto>> showScrapPost(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long memberId= customUserDetails.getMemberId();
        List<PostResponseDto> responseList  = postService.findAllScrap(memberId);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/api/posts/on-going")
    public ResponseEntity<List<PostResponseDto>> showOnGoingPost() {
        List<Post> onGoingPostList = postService.findOnGoingPostAll();
        if (onGoingPostList == null || onGoingPostList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 빈 목록일 경우 noContent 상태 코드 반환
        }
        List<PostResponseDto> responseList = onGoingPostList.stream()
                .map(PostResponseDto::new)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/api/posts/finish")
    public ResponseEntity<List<PostResponseDto>> showFinishPost() {
        List<Post> finishPostList = postService.findFinishPostAll();
        if (finishPostList == null || finishPostList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 빈 목록일 경우 noContent 상태 코드 반환
        }
        List<PostResponseDto> responseList = finishPostList.stream()
                .map(PostResponseDto::new)
                .toList();
        return ResponseEntity.ok(responseList);
    }

}
