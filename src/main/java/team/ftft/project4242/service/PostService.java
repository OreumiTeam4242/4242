package team.ftft.project4242.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TeamService teamService;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository,TeamService teamService,MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.teamService = teamService;
        this.memberRepository = memberRepository;
    }

    public Post save(PostRequestDto request) {
        Member member = memberRepository.findById(6L).orElse(null);
        Team team = Team.builder()
                .leader_id(member.getMember_id())
                .is_completed(false)
                .build();
        teamService.save(team);
        Post post = postRepository.save(request.toEntity(member, team));
        return post;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found id : " + id));
    }

    @Transactional
    public Post update(Long id, PostRequestDto request) {
        Post post = findById(id);
        post.update(request.getTitle(), request.getContent());

        return post;
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
