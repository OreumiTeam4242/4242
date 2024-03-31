package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Comment;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.CommentRequestDto;
import team.ftft.project4242.dto.CommentResponseDto;
import team.ftft.project4242.service.CommentService;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long postId,
                                                         @RequestBody CommentRequestDto request){
        Long memberId = 6L;
        CommentResponseDto response = commentService.saveComment(request,postId,memberId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    // use_yn 사용 -> db에서 영구적으로 삭제가 아닌 사용만 제한
    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long commentId){
        CommentResponseDto response = commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
