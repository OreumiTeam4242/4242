package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.dto.AddApplyRequestDto;
import team.ftft.project4242.dto.AddApplyResponseDto;
import team.ftft.project4242.service.ApplyService;

import java.util.List;

@RestController
public class ApplyController {
    private ApplyService applyService;

    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    // POST : 신청글 생성
    @PostMapping("/api/apply")
    public ResponseEntity<AddApplyResponseDto> addApply (@RequestBody AddApplyRequestDto request) {
        Apply apply = applyService.saveApply(request);
        AddApplyResponseDto response = apply.toResponse();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // GET : 신청글 목록 조회
    @GetMapping("/api/apply")
    public ResponseEntity<List<AddApplyResponseDto>> showAllApply() {
        List<Apply> applyList = applyService.findAllApply();
        List<AddApplyResponseDto> applyResponseList = applyList.stream()
                .map(AddApplyResponseDto::new)
                .toList();
        return ResponseEntity.ok(applyResponseList);
    }

    // GET : 신청 모집글 상세 조회
    @GetMapping("/api/apply/{apply_id}")
    public ResponseEntity<AddApplyResponseDto> showOneApply(@PathVariable Long apply_id) {
        Apply apply = applyService.findById(apply_id);
        return ResponseEntity.ok(apply.toResponse());
    }



}
