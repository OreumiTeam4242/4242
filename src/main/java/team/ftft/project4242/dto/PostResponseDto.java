package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Post;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;

    public PostResponseDto(Post post) {
        title = post.getTitle();
        content = post.getContent();
    }

}
