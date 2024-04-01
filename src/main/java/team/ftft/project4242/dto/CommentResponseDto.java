package team.ftft.project4242.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team.ftft.project4242.domain.Comment;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDto {
    private String content;
    private String nickname;
    private LocalDateTime created_at;
    private Boolean use_yn;

    public CommentResponseDto(Comment comment){
        this.nickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.created_at = comment.getCreatedAt();
        this.use_yn = comment.isUse_yn();
    }

}
