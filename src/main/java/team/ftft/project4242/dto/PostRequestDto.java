package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private int member_cnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Member member;

    public Post toEntity(Member member) {
        return Post.builder()
                .title(title)
                .content(content)
                .use_yn(true)
                .is_closed(false)
                .member(member)
                .build();
    }
}
