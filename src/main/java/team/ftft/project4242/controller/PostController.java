package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.dto.AddTeamRequestDto;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.dto.PostTeamRequestDto;
import team.ftft.project4242.service.PostService;
import team.ftft.project4242.service.TeamService;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private final TeamService teamService;

    public PostController(PostService postService, TeamService teamService) {
        this.postService = postService;
        this.teamService = teamService;
    }

    @PostMapping("/api/post")
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostTeamRequestDto request) {
        Post post = postService.save(request);
        Team team = teamService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(post.toResponse());
    }

    @GetMapping ("/api/post")
    public ResponseEntity<List<PostResponseDto>> showPost() {
        List<Post> postList = postService.findAll();
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

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<Void> deletedPost(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
