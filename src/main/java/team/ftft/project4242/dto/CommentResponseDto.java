package team.ftft.project4242.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {
    private String content;
    private String nickname;
    private LocalDateTime created_at;
    private Boolean use_yn;

}
