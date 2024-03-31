package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Team;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private Member member;

    public Post toEntity(Member member, Team team) {
        return Post.builder()
                .title(title)
                .content(content)
                .use_yn(true)
                .is_closed(false)
                .member(member)
                .team(team)
                .build();
    }
}
