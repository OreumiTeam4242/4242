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
public class TeamResponseDto {
    // 팀 아이디, 종료 여부, [모집글 아이디], [팀 회원], 팀장 아이디, 사용가능 여부
    private Long team_id;
    private boolean is_completed;
    private String leader;
    private boolean use_yn;
    private String type;
    private String major;


    public TeamResponseDto(Team team) {
        this.team_id = team.getTeam_id();
        this.is_completed = team.is_completed();
        this.leader = team.getPost().getMember().getNickname();
        this.use_yn = team.isUse_yn();
        this.type = team.getPost().getPostType().getType_nm();
        this.major = team.getPost().getPostMajor().getMajor_nm();
    }

}
