package team.ftft.project4242.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private String commentId;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private PostRequestDto post;
    private Boolean useYN;
    private MemberDto member;
}
