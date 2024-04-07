package team.ftft.project4242.service;

import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.repository.ApplyRepository;
import team.ftft.project4242.repository.TeamMemberRepository;
import team.ftft.project4242.repository.TeamRepository;

@Service
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final ApplyRepository applyRepository;
    private final TeamRepository teamRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository, ApplyRepository applyRepository, TeamRepository teamRepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.applyRepository = applyRepository;
        this.teamRepository = teamRepository;
    }

    public TeamMember findById(Long apply_id) {
        Apply apply = applyRepository.findById(apply_id)
                .orElseThrow(() -> new IllegalArgumentException("not found id" + apply_id));
        Member member = apply.getMember();
        Post post = apply.getPost();
        Team team = teamRepository.findByPostId(post);

        if (team == null) {
            throw new IllegalStateException("Team not found for the given apply: " + apply_id);
        }

        TeamMember teamMember = new TeamMember(member, team);
        return teamMemberRepository.save(teamMember);

    }
}