package team.ftft.project4242.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.dto.CommentRequestDto;
import team.ftft.project4242.dto.CommentResponseDto;
import team.ftft.project4242.service.CommentService;

@Tag(name = "댓글 생성,삭제")
@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "댓글 생성", description = "스터디/프로젝트 모집글에 대한 댓글 생성")
    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long postId,
                                                         @RequestBody CommentRequestDto request,
                                                        @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long memberId = customUserDetails.getMemberId();
        CommentResponseDto response = commentService.saveComment(request,postId,memberId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
    // use_yn 사용 -> db에서 영구적으로 삭제가 아닌 사용만 제한
    @Operation(summary = "댓글 삭제", description = "스터디/프로젝트 모집글 페이지에서 수행되는 댓글 삭제 기능")
    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        CommentResponseDto response = commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
