package team.ftft.project4242.controller;

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
    public ResponseEntity<List<PostResponseDto>> showArticle() {
        List<Post> articleList = postService.findAll();
        List<PostResponseDto> responseList = articleList.stream()
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
    public ResponseEntity<PostResponseDto> showPost(@PathVariable Long id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok(post.toResponse());
    }
}
