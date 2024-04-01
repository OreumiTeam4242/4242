package team.ftft.project4242.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;

import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.service.PostService;
import team.ftft.project4242.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {
    private final PostService postService;
    private final TeamService teamService;

    public PostController(PostService postService, TeamService teamService) {
        this.postService = postService;
        this.teamService = teamService;
    }

    @PostMapping("/api/post")
    public ResponseEntity<PostResponseDto> addPost( @RequestBody PostRequestDto request) {
        Post post = postService.save(request);
        post.setViewCount(0L);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(post.toResponse());
    }

    @GetMapping ("/api/post")
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
    public ResponseEntity<Post> updatePost(@PathVariable Long post_id, @RequestBody PostRequestDto request) {
        Post updatedPost = postService.update(post_id, request);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/api/post/{post_id}")
    public ResponseEntity<PostResponseDto> showPostById(@PathVariable Long post_id) {
        Post post = postService.findById(post_id);
        post.setViewCount(post.getViewCount() + 1);
        return ResponseEntity.ok(post.toResponse());
    }

    @Transactional
    @PutMapping("/api/post/{post_id}/disable")
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

}
