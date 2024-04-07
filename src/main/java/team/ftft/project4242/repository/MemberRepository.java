package team.ftft.project4242.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.ftft.project4242.domain.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);

    @Modifying
    @Query("update Member m set m.is_suspended = CASE WHEN m.is_suspended = true THEN false ELSE true END where m.member_id = :memberId")
    void toggleSuspend(Long memberId);

    @Modifying
    @Query("UPDATE Member m SET m.is_suspended = false, m.suspendedUntil = null WHERE m.is_suspended = true AND m.suspendedUntil <= :date")
    void updateSuspendedMembers(LocalDate date);
}
