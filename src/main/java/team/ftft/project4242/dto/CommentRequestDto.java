package team.ftft.project4242.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.ftft.project4242.domain.Comment;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;


@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;

    @Builder
    public CommentRequestDto(String content) {
        this.content = content;
    }

    public Comment toEntity(Post post, Member member){
        return Comment.builder()
                .content(content)
                .member(member)
                .post(post)
                .build();
    }
}
