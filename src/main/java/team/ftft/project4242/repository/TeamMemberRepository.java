package team.ftft.project4242.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.domain.TeamMember;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>{
    @Query("select p from TeamMember p where p.use_yn = true and p.team.is_completed = false and p.member.member_id=:memberId ")
    List<TeamMember> findOnGoingTeam(Long memberId);

    @Query("select p from TeamMember p where p.use_yn = true and p.team.is_completed = true and p.member.member_id=:memberId ")
    List<TeamMember> findFinishedTeam(Long memberId);
}