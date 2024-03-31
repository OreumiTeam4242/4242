package team.ftft.project4242.dto;

import lombok.Getter;
import team.ftft.project4242.domain.Comment;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;

import java.time.LocalDateTime;

@Getter
public class CommentRequestDto {
    private String content;

    public Comment toEntity(Post post, Member member){
        return Comment.builder()
                .content(content)
                .member(member)
                .post(post)
                .build();
    }
}
