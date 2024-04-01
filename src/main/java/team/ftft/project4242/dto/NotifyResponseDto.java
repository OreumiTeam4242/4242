package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Notify;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotifyResponseDto {
    // 신고글 아이디, 제목, 사유, 생성일시, [작성자], [신고유저], 파일 아이디
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public NotifyResponseDto(Notify notify) {
        title = notify.getTitle();
        content = notify.getContent();
        createdAt = notify.getCreatedAt();
    }
}
