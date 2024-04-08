package team.ftft.project4242.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.dto.*;
import team.ftft.project4242.repository.ApplyRepository;
import team.ftft.project4242.service.ApplyService;
import team.ftft.project4242.service.TeamMemberService;


@Tag(name = "스터디/프로젝트 지원글 CUD")
@RestController
public class ApplyController {
    private ApplyService applyService;
    private TeamMemberService teamMemberService;
    private ApplyRepository applyRepository;

    public ApplyController(ApplyService applyService, TeamMemberService teamMemberService, ApplyRepository applyRepository) {
        this.applyService = applyService;
        this.teamMemberService = teamMemberService;
        this.applyRepository = applyRepository;
    }

    // POST : 신청글 생성
    @Operation(summary = "신청글 생성", description = "스터디/프로젝트 모집글에 대한 신청글 생성")
    @PostMapping("/api/post/{post_id}/apply")
    public ResponseEntity<ApplyResponseDto> addApply(@RequestPart ApplyRequestDto request,
                                                     @RequestPart(value = "file", required = false) MultipartFile file,
                                                     @PathVariable Long post_id,
                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getMemberId();
        Apply apply = applyService.saveApply(request, post_id, file, memberId);
        ApplyResponseDto response = apply.toResponse();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // POST: 스터디 팀원 추가
    @Operation(summary = "스터디 팀원 추가", description = "신청글 페이지에서 수행되는 팀원 추가 기능")
    @PostMapping("/api/apply/{apply_id}/accept")
    public ResponseEntity<?> acceptApply(@PathVariable Long apply_id) {
        teamMemberService.findById(apply_id);
        return ResponseEntity.ok().build();
    }
}
