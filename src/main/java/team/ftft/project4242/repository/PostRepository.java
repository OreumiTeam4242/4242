package team.ftft.project4242.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.ftft.project4242.domain.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post  p where p.use_yn = true")
    List<Post> findAllAble();
  
    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.post_id = :id AND p.use_yn = true")
    void incrementViewCountForActivePost(Long id);

    @Query("select p from Post p where p.use_yn = true and p.post_id = :post_id")
    Post findByIdAble(Long post_id);

    @Modifying
    @Query("update Post p set p.use_yn = false where p.post_id = :id")
    void disablePostById(Long id);

    @Query("select p from Post  p where p.use_yn = true and p.postType.type_id = :type_id")
    List<Post> findTypePostAll(Long type_id);

    @Query("select p from Post  p where p.use_yn = true and p.postMajor.major_id = :major_id")
    List<Post> findMajorPostAll(Long major_id);

    @Query("select p from Post p order by p.viewCount desc limit 3")
    List<Post> findTop3PostsByViewCount();

    @Query("select p from Post p where p.use_yn = true and p.is_closed = false and p.team.is_completed = false")
    List<Post> findOnGoingPostAll();

    @Query("select p from Post p where p.use_yn = true and p.is_closed = true and p.team.is_completed = true")
    List<Post> findFinishPostAll();

    @Query("select p from Post p where p.use_yn = true and p.member.member_id = :memberId and p.is_closed=false")
    List<Post> findAllByMemberId(Long memberId);

    @Modifying
    @Query("update Post p set p.is_closed = true where p.start_date <= CURRENT_DATE and p.is_closed = false")
    void closePosts();

    @Modifying
    @Query("update Post p set p.is_closed = true where p.is_closed = false and p.post_id = :postId")
    void closePost(Long postId);
}
