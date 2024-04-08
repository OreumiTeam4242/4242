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
    private Long commentId;
    private Long member_id;
    private String content;
    private String nickname;
    private LocalDateTime created_at;
    private Boolean use_yn;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getComment_id();
        this.member_id = comment.getMember().getMember_id();
        this.nickname = comment.getMember().getNickname();
        this.content = comment.getContent();
        this.created_at = comment.getCreatedAt();
        this.use_yn = comment.isUse_yn();
    }

    public CommentResponseDto toResponse(){
        return CommentResponseDto.builder()
                .commentId(commentId)  // commentId를 설정
                .content(content)
                .nickname(nickname)
                .created_at(created_at)
                .use_yn(use_yn)
                .build();
    }

}
