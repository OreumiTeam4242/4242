package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private Long type_id;
    private Long major_id;

    public Post toEntity(Member member, Team team, PostType postType, PostMajor postMajor) {
        return Post.builder()
                .title(title)
                .content(content)
                .postType(postType)
                .postMajor(postMajor)
                .member(member)
                .team(team)
                .build();
    }
}
