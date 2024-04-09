package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Role;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberRequestDto {
    private Long member_id;
    private String email;
    private String password;
    private String nickname;
    private boolean use_yn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String img_url;
    private Role role;

    public MemberResponseDto toResponse() {
        return MemberResponseDto.builder()
                .member_id(this.member_id)
                .email(this.email)
                .nickname(this.nickname)
                .use_yn(this.use_yn)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .img_url(this.img_url)
                .role(this.role.value())
                .isAdmin(this.role.value().equals("관리자"))
                .build();
    }
}