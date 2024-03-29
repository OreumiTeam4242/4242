package team.ftft.project4242.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.ftft.project4242.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
