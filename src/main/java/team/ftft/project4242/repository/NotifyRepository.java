package team.ftft.project4242.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.ftft.project4242.domain.Notify;
@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {
}
