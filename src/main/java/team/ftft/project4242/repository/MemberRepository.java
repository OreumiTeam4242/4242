package team.ftft.project4242.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.ftft.project4242.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Member findByEmailAndPassword(String email, String password);
}
