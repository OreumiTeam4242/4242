package team.ftft.project4242.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.service.ScrapService;

@Tag(name = "스크랩(즐겨찾기) 수정,조회")
@RestController
public class ScrapController {
    private final ScrapService scrapService;

    public ScrapController(ScrapService scrapService) {
        this.scrapService = scrapService;
    }

    @Operation(summary = "모집글 스크랩 수정", description = "모집글 스크랩 수정 -> 이미 존재하면 삭제하고, 존재하지 않으면 생성")
    @PostMapping("/api/posts/{postId}/scraps")
    public ResponseEntity<HttpStatus> scrapPost(@PathVariable Long postId
                                                ,@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long memberId = customUserDetails.getMemberId();
        scrapService.updateScrap(postId,memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "모집글 스크랩 상태 조회", description = "해당 모집글의 스크랩 상태를 조회")
    @GetMapping("/api/posts/{postId}/scraps/status")
    public boolean getScrapStatus(@PathVariable Long postId
                                            ,@AuthenticationPrincipal CustomUserDetails customUserDetails){
        // postId를 사용하여 DB에서 해당 포스트의 스크랩 상태를 조회합니다.
        Long memberId = customUserDetails.getMemberId();
        return scrapService.isScrapped(postId, memberId); // scrapService는 해당 포스트의 스크랩 상태를 확인하는 서비스입니다.
    }
}
