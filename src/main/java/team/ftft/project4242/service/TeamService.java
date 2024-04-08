package team.ftft.project4242.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.domain.TeamMember;
import team.ftft.project4242.dto.TeamResponseDto;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.repository.TeamMemberRepository;
import team.ftft.project4242.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PostRepository postRepository;
    public TeamService(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository, PostRepository postRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.postRepository = postRepository;
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
        // 팀 종료시 모집글 삭제 -> 참조 문제로 삭제가 아닌 is_closed true로 변경
        Long postId = team.getPost().getPost_id();
        postRepository.closePost(postId);
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

