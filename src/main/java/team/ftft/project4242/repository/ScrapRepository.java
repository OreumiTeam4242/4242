package team.ftft.project4242.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Scrap;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap,Long> {
    Optional<Scrap> findByPostAndMember(Post post, Member member);

    Optional<List<Scrap>> findAllByMember(Member member);

    @Query("select s from Scrap s where s.post.post_id = :postId and s.member.member_id = :memberId")
    Scrap existsByPostIdAndMemberId(Long postId, Long memberId);
}
