package team.ftft.project4242.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Notify;
import team.ftft.project4242.dto.AddNotifyRequestDto;
import team.ftft.project4242.dto.AddNotifyResponseDto;
import team.ftft.project4242.service.NotifyService;

import java.util.List;

@RestController
public class NotifyController {
    private NotifyService notifyService;
    public NotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    // POST : 신고글 생성
    @PostMapping("/api/notify")
    public ResponseEntity<AddNotifyResponseDto> addNotify (@RequestBody AddNotifyRequestDto request) {
        Notify notify = notifyService.saveNotify(request);
        AddNotifyResponseDto response = notify.toResponse();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // GET : 신고글 목록 조회 - 관리자 권한 부여해야함
    @GetMapping("/api/notify")
    public ResponseEntity<List<AddNotifyResponseDto>> showAllNotify() {
        List<Notify> notifyList = notifyService.findAllNotify();
        List<AddNotifyResponseDto> notifyResponseList = notifyList.stream()
                .map(AddNotifyResponseDto::new)
                .toList();
        return ResponseEntity.ok(notifyResponseList);
    }

    // GET : 신청 모집글 상세 조회 - 관리자 권한 부여해야함
    @GetMapping("/api/notify/{notify_id}")
    public ResponseEntity<AddNotifyResponseDto> showOneNotify(@PathVariable Long notify_id) {
        Notify notify = notifyService.findById(notify_id);
        return ResponseEntity.ok(notify.toResponse());
    }
}
