package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.TeamMember;
import team.ftft.project4242.dto.ApplyRequestDto;
import team.ftft.project4242.dto.ApplyResponseDto;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.service.ApplyService;
import team.ftft.project4242.service.TeamMemberService;

import java.util.List;

@RestController
public class ApplyController {
    private ApplyService applyService;
    private TeamMemberService teamMemberService;

    public ApplyController(ApplyService applyService, TeamMemberService teamMemberService) {
        this.applyService = applyService;
        this.teamMemberService = teamMemberService;
    }

    // POST : 신청글 생성
    @PostMapping("/api/post/{post_id}/apply")
    public ResponseEntity<ApplyResponseDto> addApply (@RequestPart ApplyRequestDto request,
                                                      @RequestPart(value="file",required = false) MultipartFile file,
                                                      @PathVariable Long post_id) {
        Apply apply = applyService.saveApply(request, post_id,file);
        ApplyResponseDto response = apply.toResponse();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // GET : 신청글 목록 조회
    @GetMapping("/api/applies")
    public ResponseEntity<List<ApplyResponseDto>> showAllApply() {
        List<Apply> applyList = applyService.findAllApply();
        List<ApplyResponseDto> applyResponseList = applyList.stream()
                .map(ApplyResponseDto::new)
                .toList();
        return ResponseEntity.ok(applyResponseList);
    }

    // GET : 신청 모집글 상세 조회
    @GetMapping("/api/apply/{apply_id}")
    public ResponseEntity<ApplyResponseDto> showOneApply(@PathVariable Long apply_id) {
        Apply apply = applyService.findById(apply_id);
        return ResponseEntity.ok(apply.toResponse());
    }

    // POST : 스터디 팀원 추가
    @PostMapping("/api/apply/{apply_id}/accept")
    public ResponseEntity<TeamMember> PostTeamMember(@PathVariable Long apply_id) {
        TeamMember teamMember = teamMemberService.findById(apply_id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamMember);
    }
}
