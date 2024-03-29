package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.dto.AddApplyRequest;
import team.ftft.project4242.dto.AddApplyResponse;
import team.ftft.project4242.service.ApplyService;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

    // GET : 신청글 목록 조회
    @GetMapping("/api/apply")
    public ResponseEntity<List<AddApplyResponse>> showAllApply() {
        List<Apply> applyList = applyService.findAllApply();
        List<AddApplyResponse> applyResponseList = applyList.stream()
                .map(AddApplyResponse::new)
                .toList();
        return ResponseEntity.ok(applyResponseList);
    }

    // GET : 신청 모집글 상세 조회
    @GetMapping("/api/apply/{apply_id}")
    public ResponseEntity<AddApplyResponse> showOneApply(@PathVariable Long apply_id) {
        Apply apply = applyService.findById(apply_id);
        return ResponseEntity.ok(apply.toResponse());
    }



}
