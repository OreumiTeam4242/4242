package team.ftft.project4242.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.dto.TeamResponseDto;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.repository.TeamMemberRepository;
import team.ftft.project4242.repository.TeamRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TeamServiceTest {
    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Test
    @DisplayName("팀 저장하기")
    void save() {
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
    @DisplayName("팀이 종료되었을 때 상태 업데이트")
    void updateIscompleted() {
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
    @DisplayName("진행중인 팀 찾기")
    void findOnGoingTeamAll() {
        // given
        Long memberId = 1L;

        Member member = Member.builder()
                .member_id(memberId)
                .nickname("OnGoing")
                .build();

        PostType postType = PostType.builder()
                .type_nm("Type1")
                .build();

        PostMajor postMajor = PostMajor.builder()
                .major_nm("Major1")
                .build();

        Post post1 = Post.builder()
                .member(member)
                .postType(postType)
                .postMajor(postMajor)
                .build();

        Post post2 = Post.builder()
                .member(member)
                .postType(postType)
                .postMajor(postMajor)
                .build();

        Team team1 = Team.builder()
                .team_id(1L)
                .is_completed(false)
                .post(post1)
                .build();
        Team team2 = Team.builder()
                .team_id(2L)
                .is_completed(false)
                .post(post2)
                .build();

        TeamMember teamMember1 = TeamMember.builder()
                .team(team1)
                .member(member)
                .build();

        TeamMember teamMember2 = TeamMember.builder()
                .team(team2)
                .member(member)
                .build();

        when(teamMemberRepository.findOnGoingTeam(memberId))
                .thenReturn(Arrays.asList(teamMember1, teamMember2));

        // when
        List<TeamResponseDto> result = teamService.findOnGoingTeamAll(memberId);

        // then
        assertEquals(2, result.size());
        assertEquals(false, result.get(0).is_completed());
        assertEquals(false, result.get(1).is_completed());

        verify(teamMemberRepository, times(1)).findOnGoingTeam(memberId);
    }

    @Test
    @DisplayName("종료된 팀 찾기")
    void findFinishedTeam() {
        // given
        Long memberId = 1L;

        Member member = Member.builder()
                .member_id(memberId)
                .nickname("OnGoing")
                .build();

        PostType postType = PostType.builder()
                .type_nm("Type1")
                .build();

        PostMajor postMajor = PostMajor.builder()
                .major_nm("Major1")
                .build();

        Post post1 = Post.builder()
                .member(member)
                .postType(postType)
                .postMajor(postMajor)
                .build();

        Post post2 = Post.builder()
                .member(member)
                .postType(postType)
                .postMajor(postMajor)
                .build();

        Team team1 = Team.builder()
                .team_id(1L)
                .is_completed(true)
                .post(post1)
                .build();
        Team team2 = Team.builder()
                .team_id(2L)
                .is_completed(true)
                .post(post2)
                .build();

        TeamMember teamMember1 = TeamMember.builder()
                .team(team1)
                .member(member)
                .build();

        TeamMember teamMember2 = TeamMember.builder()
                .team(team2)
                .member(member)
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