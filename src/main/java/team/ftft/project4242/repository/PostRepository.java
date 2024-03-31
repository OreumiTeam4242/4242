package team.ftft.project4242.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.ftft.project4242.domain.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post  p where p.use_yn = true")
    List<Post> findAllAble();
    @Modifying
    @Query("update Post p set p.use_yn = false where p.post_id = :id")
    void disablePostById(Long id);

    @Query("select p from Post  p where p.use_yn = true and p.postType.type_nm = :study")
    List<Post> findStudyPostAll();

    @Query("select p from Post  p where p.use_yn = true and p.postType.type_nm = :project")
    List<Post> findProjectPostAll();
}
