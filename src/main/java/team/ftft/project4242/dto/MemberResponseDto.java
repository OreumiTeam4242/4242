package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Member;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long member_id;
    private String email;
    private String password;
    private String nickname;
    private Boolean use_yn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String img_url;
    private String role;
    private Boolean isAdmin;

    public static MemberResponseDto toResponse(Member member) {
        return MemberResponseDto.builder()
                .member_id(member.getMember_id())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .use_yn(member.toResponse().getUse_yn())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .img_url(member.getImg_url())
                .build();
    }
}
