package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Team;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddTeamResponseDto {
    // 팀 아이디, 종료 여부, [모집글 아이디], [팀 회원], 팀장 아이디, 사용가능 여부
    private boolean is_completed;
    private Post post;
    private Member member;
    private String leader_id;
    private boolean use_yn;

    public AddTeamResponseDto(Team team) {
        is_completed = team.is_completed();
        post = team.getPost();
        leader_id = team.getLeader_id();
        use_yn = team.isUse_yn();
    }
}
