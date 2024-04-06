package team.ftft.project4242.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.ftft.project4242.domain.Apply;


@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    @Query("SELECT a FROM Apply a WHERE a.post.post_id = :postId")
    Page<Apply> findAllByPostId(Long postId, Pageable pageable);
}
