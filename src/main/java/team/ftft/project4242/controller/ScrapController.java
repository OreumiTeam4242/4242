package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.ftft.project4242.service.ScrapService;

@RestController
public class ScrapController {
    private final ScrapService scrapService;
    public ScrapController(ScrapService scrapService) {
        this.scrapService = scrapService;
    }

    @PostMapping("/api/posts/{postId}/scraps")
    public ResponseEntity<HttpStatus> scrapPost(@PathVariable Long postId){
        Long memberId = 6L;
        scrapService.updateScrap(postId,memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
