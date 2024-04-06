package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.TeamMember;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMemberResponseDto {
    // 사용 가능 여부, 멤버 아이디, 팀 아이디
    private Long team_member_id;
    private Long member_id;
    private Long team_id;

    public TeamMemberResponseDto(TeamMember teamMember) {
        this.team_member_id = teamMember.getTeamMemberId();
        this.member_id = teamMember.getMember().getMember_id();
//        this.team_id = teamMember.getTeam().getTeam_id();
    }

}
