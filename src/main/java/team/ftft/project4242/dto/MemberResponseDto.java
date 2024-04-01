package team.ftft.project4242.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class MemberResponseDto {
    private Long member_id;
    private String email;
    private String password;
    private String nickname;
    private Boolean use_yn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID img_id;
}
