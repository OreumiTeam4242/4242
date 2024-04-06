package team.ftft.project4242.service;

import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.*;
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

    public TeamMember findById(Long apply_id) {
        Apply apply = applyRepository.findById(apply_id)
                .orElseThrow(() -> new IllegalArgumentException("not found id" + apply_id));
        Member member = apply.getMember();
        Post post = apply.getPost();
        Team team = post.getTeam();

        if (team == null) {
            throw new IllegalStateException("Team not found for the given apply: " + apply_id);
        }

        TeamMember teamMember = new TeamMember(member, team);
        return teamMemberRepository.save(teamMember);

    }

    public TeamMember addTeamMember(Member member, Team team) {
        TeamMember teamMember = new TeamMember(member, team);
        return teamMemberRepository.save(teamMember);
    }
}