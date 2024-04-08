package team.ftft.project4242.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.domain.TeamMember;
import team.ftft.project4242.dto.TeamResponseDto;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.repository.TeamMemberRepository;
import team.ftft.project4242.repository.TeamRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
class TeamServiceTest {
    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private PostRepository postRepository;
    @BeforeEach
    public void setUp() {
        // 각 테스트 메서드 실행 전 모든 mock 객체들 재설정 해주기
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        // todo 팀 저장하기.
        // given
        Team inputTeam = Team.builder()
                .is_completed(false)
                .leader_id(1L)
                .build();

        Team savedTeam = Team.builder()
                .is_completed(false)
                .leader_id(1L)
                .build();

        when(teamRepository.save(inputTeam)).thenReturn(savedTeam);

        // when
        Team resultTeam = teamService.save(inputTeam);

        // then
        assertEquals(savedTeam.getTeam_id(), resultTeam.getTeam_id());
        assertEquals(savedTeam.getLeader_id(), resultTeam.getLeader_id());
        assertEquals(savedTeam.is_completed(), resultTeam.is_completed());
    }



    @Test
    void updateIscompleted() {
        // todo 팀이 종료되었을 때 상태 업데이트 하기.
        // given
        Long teamId = 1L;

        Team team = Team.builder()
                .team_id(teamId)
                .is_completed(false)
                .build();

        Member member = Member.builder()
                .member_id(1L)
                .build();

        TeamMember teamMember = TeamMember.builder()
                .member(member)
                .build();

        team.setTeamMemberList(Collections.singletonList(teamMember));

        Post post = Post.builder()
                .post_id(1L)
                .build();

        team.setPost(post);
        when(teamRepository.findById(teamId)).thenReturn(java.util.Optional.of(team));

        // when
        Team updatedTeam = teamService.updateIscompleted(teamId);

        // then
        assertNotNull(updatedTeam);
        assertTrue(updatedTeam.is_completed());
        assertEquals(1, member.getPostCount());
    }

    @Test
    void findOnGoingTeamAll() {
        // todo 진행중인 팀 찾기.
        // given
        Long memberId = 1L;
        Team team1 = Team.builder()
                .team_id(1L)
                .is_completed(false)
                .build();
        Team team2 = Team.builder()
                .team_id(2L)
                .is_completed(false)
                .build();

        TeamMember teamMember1 = TeamMember.builder()
                .team(team1)
                .build();
        TeamMember teamMember2 = TeamMember.builder()
                .team(team2)
                .build();

        when(teamMemberRepository.findOnGoingTeam(memberId))
                .thenReturn(Arrays.asList(teamMember1, teamMember2));

        // when
        List<TeamResponseDto> result = teamService.findOnGoingTeamAll(memberId);

        // then
        assertEquals(2, result.size());
        assertEquals(false, result.get(0).is_completed());
        assertEquals(false, result.get(1).is_completed());
    }

    @Test
    void findFinishedTeam() {
        // todo 종료된 팀 찾기
        // given
        Long memberId = 1L;

        Team team1 = Team.builder()
                .team_id(1L)
                .is_completed(true)
                .build();
        Team team2 = Team.builder()
                .team_id(2L)
                .is_completed(true)
                .build();

        TeamMember teamMember1 = TeamMember.builder()
                .team(team1)
                .build();
        TeamMember teamMember2 = TeamMember.builder()
                .team(team2)
                .build();

        when(teamMemberRepository.findFinishedTeam(memberId))
                .thenReturn(Arrays.asList(teamMember1, teamMember2));

        // when
        List<TeamResponseDto> result = teamService.findFinishedTeam(memberId);

        // then
        assertEquals(2, result.size());
        assertEquals(true, result.get(0).is_completed());
        assertEquals(true, result.get(1).is_completed());
    }
}