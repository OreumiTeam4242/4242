package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.dto.AddApplyRequest;
import team.ftft.project4242.dto.AddApplyResponse;
import team.ftft.project4242.service.ApplyService;

@RestController
public class ApplyController {
    private ApplyService applyService;

    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    // POST : 신청글 생성
    @PostMapping("/api/apply")
    public ResponseEntity<AddApplyResponse> addApply (@RequestBody AddApplyRequest request) {
        Apply apply = applyService.saveApply(request);
        AddApplyResponse response = apply.toResponse();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
