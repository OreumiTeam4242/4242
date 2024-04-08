package team.ftft.project4242.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.repository.ApplyRepository;
import team.ftft.project4242.repository.TeamMemberRepository;
import team.ftft.project4242.repository.TeamRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TeamMemberServiceTest {
    @InjectMocks
    private TeamMemberService teamMemberService;

    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private ApplyRepository applyRepository;
    @Mock
    private TeamRepository teamRepository;

    @Test
    void findById() {
        // todo 신청글을 수락했을 때 teamMember 테이블에 팀원 member_id, post_id 저장하기.
        // given
        Long applyId = 1L;
        Apply apply = Apply.builder()
                .apply_id(applyId)
                .member(Member.builder().member_id(1L).build())
                .post(Post.builder().post_id(1L).build())
                .build();
        Team team = Team.builder()
                .team_id(1L)
                .build();

        when(applyRepository.findById(applyId)).thenReturn(Optional.of(apply));
        when(teamRepository.findByPostId(any(Post.class))).thenReturn(team);

        // when
        TeamMember savedTeamMember = teamMemberService.findById(applyId);

        // then
        assertNotNull(savedTeamMember);
        assertEquals(apply.getMember().getMember_id(), savedTeamMember.getMember().getMember_id());
        assertEquals(team.getTeam_id(), savedTeamMember.getTeam().getTeam_id());
    }
}