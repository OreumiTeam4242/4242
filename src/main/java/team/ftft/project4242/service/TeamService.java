package team.ftft.project4242.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.domain.TeamMember;
import team.ftft.project4242.dto.TeamResponseDto;
import team.ftft.project4242.repository.TeamMemberRepository;
import team.ftft.project4242.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    public TeamService(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
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

    public List<TeamResponseDto> findOnGoingTeamAll(Long memberId){
       List<TeamMember> teamMemberList = teamMemberRepository.findOnGoingTeam(memberId);
       List<TeamResponseDto> teamList = teamMemberList.stream()
               .map(teamMember -> new TeamResponseDto(teamMember.getTeam()))
               .collect(Collectors.toList());

        return teamList;
    }

    public List<TeamResponseDto> findFinishedTeam(Long memberId){
        List<TeamMember> teamMemberList = teamMemberRepository.findFinishedTeam(memberId);
        List<TeamResponseDto> FinishedTeamList = teamMemberList.stream()
                .map(teamMember -> new TeamResponseDto(teamMember.getTeam()))
                .collect(Collectors.toList());
        return FinishedTeamList;
    }


}

