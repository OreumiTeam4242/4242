package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Team;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddTeamRequest {
    // 팀 아이디, 종료 여부, [모집글 아이디], [팀 회원], 팀장 아이디, 사용가능 여부
    private boolean is_completed;
    private Post post;
    private Member member;
    private String leader_id;
    private boolean use_yn;

    public Team toEntity() {
        return Team.builder() // 생성자를 사용해 객체 생성
                .is_completed(is_completed)
                .post(post)
                .member(member)
                .leader_id(leader_id)
                .use_yn(use_yn)
                .build();
    }
}
