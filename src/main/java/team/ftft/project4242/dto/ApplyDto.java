package team.ftft.project4242.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApplyDto {
    private String applyId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private PostRequestDto post;
    private MemberDto member;
}
