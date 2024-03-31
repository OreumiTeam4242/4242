package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.PostMajor;
import team.ftft.project4242.domain.PostType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;
    private int member_cnt;
    private PostType postType;
    private PostMajor postMajor;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(Post post) {
        title = post.getTitle();
        content = post.getContent();
        member_cnt = post.getMember_cnt();

        createdAt = post.getCreatedAt();
        updatedAt = post.getUpdatedAt();
    }
}
