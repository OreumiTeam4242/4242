package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.domain.Team;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyResponseDto {
    // 신청글 아이디, 제목, 신청 내용, [모집글 아이디], [회원 아이디], 파일 아이디(매핑 전), 가능 시간, 가능 요일
    private Long id;
    private String title;
    private String content;
    private String available_time;
    private String available_day;
    private String file_url;
    private String nickname;
    private LocalDateTime createdAt;

    public ApplyResponseDto(Apply apply) {
        id = apply.getApply_id();
        title = apply.getTitle();
        content = apply.getContent();
        available_time = apply.getAvailable_time();
        available_day = apply.getAvailable_day();
        file_url = apply.getFile_url();
        nickname = apply.getMember().getNickname();
        createdAt = apply.getCreatedAt();
    }
}
