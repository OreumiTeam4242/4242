package team.ftft.project4242.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private String memberId;
    private String email;
    private String password;
    private String nickname;
    private Boolean useYN;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
