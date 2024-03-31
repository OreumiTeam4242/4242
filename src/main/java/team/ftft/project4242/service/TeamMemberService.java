package team.ftft.project4242.service;

import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.domain.TeamMember;
import team.ftft.project4242.repository.ApplyRepository;
import team.ftft.project4242.repository.TeamMemberRepository;

@Service
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final ApplyRepository applyRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository, ApplyRepository applyRepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.applyRepository = applyRepository;
    }

    public TeamMember findById(Long member_id) {
        Apply apply = applyRepository.findById(member_id)
                .orElseThrow(() -> new IllegalArgumentException("not found id" + member_id));
        TeamMember teamMember = convertToTeamMember(apply);
        return teamMemberRepository.save(teamMember.toResponse());

    }

    private TeamMember convertToTeamMember(Apply apply) {
        TeamMember teamMember = new TeamMember();
        teamMember.getTeamMemberId();
        teamMember.getLeader_id();
        return teamMember;

    }
}