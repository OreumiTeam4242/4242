package team.ftft.project4242.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.domain.TeamMember;
import team.ftft.project4242.repository.TeamRepository;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Transactional
    public Team updateIscompleted(Long team_id) {
        Team team = teamRepository.findById(team_id)
                .orElseThrow(() -> new IllegalArgumentException("not found " + team_id));
        for(TeamMember teamMember : team.getTeamMemberList()){
            Member member = teamMember.getMember();
            member.increasePostCount();
            member.checkAndUpgradeRole();
        }
        team.updateIscompleted();
        return team;
    }

}

