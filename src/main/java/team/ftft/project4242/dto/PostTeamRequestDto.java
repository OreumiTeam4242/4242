package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Team;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostTeamRequestDto {
    // post
    private String title;
    private String content;
    private MemberDto member;
    private Boolean isClosed;
    private Boolean useYN;

    // team
    private boolean is_completed;
    private Post post;
    private String leader_id;
    private boolean use_yn;

    public Post toPostEntity() {
        return Post.builder()
                // post
                .title(title)
                .content(content)

                // team
                .is_completed(is_completed)
                .leader_id(leader_id)
                .use_yn(use_yn)
                .build();
    }

    public Team toTeamEntity() {
        return Team.builder()
                // post
                .title(title)
                .content(content)

                // team
                .is_completed(is_completed)
                .leader_id(leader_id)
                .use_yn(use_yn)
                .build();
    }
}
