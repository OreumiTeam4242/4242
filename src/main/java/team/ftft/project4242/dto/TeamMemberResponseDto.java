package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Team;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMemberResponseDto {
    private Member member_id;
    private Long leader_id;

    public TeamMemberResponseDto(Team team) {
        member_id = getMember_id();
        leader_id = team.getLeader_id();
    }
}