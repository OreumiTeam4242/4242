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
//    private int member_cnt;

    private Long type_id;
    private Long major_id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(Post post) {
        title = post.getTitle();
        content = post.getContent();

        type_id = post.getPostType().getType_id();
        major_id = post.getPostMajor().getMajor_id();

        createdAt = post.getCreatedAt();
        updatedAt = post.getUpdatedAt();
    }
}
