package team.ftft.project4242.service;

import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Scrap;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.repository.ScrapRepository;

@Service
public class ScrapService {
    private final PostRepository postRepository;
    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;
    public ScrapService(PostRepository postRepository, ScrapRepository scrapRepository,MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.scrapRepository = scrapRepository;
        this.memberRepository = memberRepository;
    }

    public void updateScrap(Long postId,Long memberId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("post doesn't exist"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member doesn't exist"));

        if (!hasScrap(post, member)) {
            createScrap(post, member);
        } else {
            deleteScrap(post, member);
        }
    }
    private boolean hasScrap(Post post, Member member){
        return scrapRepository.findByPostAndMember(post,member).isPresent();
    }
    private void createScrap(Post post,Member member){
        Scrap scrap = new Scrap(post,member);
        scrapRepository.save(scrap);
    }
    private void deleteScrap(Post post,Member member){
        Scrap scrap = scrapRepository.findByPostAndMember(post,member)
                .orElseThrow(()->new IllegalArgumentException("scrap doesn't exist"));
        scrapRepository.delete(scrap);

    }

}
