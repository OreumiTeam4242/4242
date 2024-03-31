package team.ftft.project4242.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.service.PostService;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/post")
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostRequestDto request) {
        Post post = postService.save(request);
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

    @PutMapping("/api/post/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostRequestDto request) {
        Post updatedPost = postService.update(id, request);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity<PostResponseDto> showPostById(@PathVariable Long id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok(post.toResponse());
    }

    @Transactional
    @PutMapping("/api/post/{id}/disable")
    public ResponseEntity<Void> disablePostById(@PathVariable Long id) {
        postService.disablePostById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/posts/study")
    public ResponseEntity<List<PostResponseDto>> showStudyPost() {
        List<Post> studyPostList = postService.findStudyPostAll();
        if (studyPostList == null || studyPostList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 빈 목록일 경우 noContent 상태 코드 반환
        }
        List<PostResponseDto> responseList = studyPostList.stream()
                .map(PostResponseDto::new)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/api/posts/project")
    public ResponseEntity<List<PostResponseDto>> showProjectPost() {
        List<Post> projectPostList = postService.findProjectPostAll();
        if (projectPostList == null || projectPostList.isEmpty()) {
            return ResponseEntity.noContent().build(); // 빈 목록일 경우 noContent 상태 코드 반환
        }
        List<PostResponseDto> responseList = projectPostList.stream()
                .map(PostResponseDto::new)
                .toList();
        return ResponseEntity.ok(responseList);
    }
}
