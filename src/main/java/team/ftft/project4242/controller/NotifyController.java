package team.ftft.project4242.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.domain.Notify;
import team.ftft.project4242.dto.NotifyRequestDto;
import team.ftft.project4242.dto.NotifyResponseDto;
import team.ftft.project4242.service.NotifyService;

import java.util.List;

@Tag(name = "신고글 CUD")
@RestController
public class NotifyController {
    private final NotifyService notifyService;
    public NotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    // POST : 신고글 생성
    @Operation(summary = "신고글 생성", description = "신고글 생성")
    @PostMapping("/api/notify")
    public ResponseEntity<?> addNotify (@RequestPart NotifyRequestDto request,
                                                        @RequestPart(value="file",required = false) MultipartFile file
                                                        ,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getMemberId();

        if(!notifyService.isMemberExists(request.getNotifyMemberName())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Notify notify = notifyService.saveNotify(request,file,memberId);
        NotifyResponseDto response = notify.toResponse();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}
