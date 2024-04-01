package team.ftft.project4242.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.PostMajorRepository;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.repository.PostTypeRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTypeRepository postTypeRepository;
    private final PostMajorRepository postMajorRepository;
    private final MemberRepository memberRepository;
    private final TeamService teamService;

    public PostService(PostRepository postRepository, PostTypeRepository postTypeRepository, PostMajorRepository postMajorRepository,MemberRepository memberRepository,TeamService teamService) {
        this.postRepository = postRepository;
        this.postTypeRepository = postTypeRepository;
        this.postMajorRepository = postMajorRepository;
        this.memberRepository = memberRepository;
        this.teamService = teamService;
    }

    public Post save(PostRequestDto request) {

        Member member = memberRepository.findById(4L).orElse(null);
        Team team = Team.builder()
                .leader_id(member.getMember_id())
                .is_completed(false)
                .build();
        teamService.save(team);
      
        PostType postType = postTypeRepository.findById(request.getType_id())
                .orElseThrow(() -> new IllegalArgumentException("not found type id"));
        PostMajor postMajor = postMajorRepository.findById(request.getMajor_id())
                .orElseThrow(() -> new IllegalArgumentException("not found major id"));

        Post post = postRepository.save(request.toEntity(member, team, postType,postMajor));
     
        return post;
    }

    public List<Post> findAllAble() {
        return postRepository.findAllAble();
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

    public void disablePostById(Long id) {
        postRepository.disablePostById(id);
    }

    public List<Post> findTypePostAll(Long type_id) {
        return postRepository.findTypePostAll(type_id);
    }

    public List<Post> findMajorPostAll(Long major_id) {
        return postRepository.findMajorPostAll(major_id);
    }

    public List<Post> findTop3PostsByViewCount() {
        return postRepository.findTop3PostsByViewCount();
    }
}
