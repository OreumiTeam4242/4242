package team.ftft.project4242.service;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.dto.PostRequestDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.repository.*;
import team.ftft.project4242.service.file.AwsS3Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTypeRepository postTypeRepository;
    private final PostMajorRepository postMajorRepository;
    private final MemberRepository memberRepository;
    private final TeamService teamService;
    private final ScrapRepository scrapRepository;
    private final AwsS3Service awsS3Service;
    private final TeamMemberRepository teamMemberRepository;

    public PostService(PostRepository postRepository, PostTypeRepository postTypeRepository, PostMajorRepository postMajorRepository, MemberRepository memberRepository, TeamService teamService, ScrapRepository scrapRepository, AwsS3Service awsS3Service, TeamMemberRepository teamMemberRepository) {
        this.postRepository = postRepository;
        this.postTypeRepository = postTypeRepository;
        this.postMajorRepository = postMajorRepository;
        this.memberRepository = memberRepository;
        this.teamService = teamService;
        this.scrapRepository = scrapRepository;
        this.awsS3Service = awsS3Service;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Transactional
    public Post save(PostRequestDto request, @Nullable MultipartFile file,Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("not found type id"));
        PostType postType = postTypeRepository.findById(request.getType_id())
                .orElseThrow(() -> new IllegalArgumentException("not found type id"));
        PostMajor postMajor = postMajorRepository.findById(request.getMajor_id())
                .orElseThrow(() -> new IllegalArgumentException("not found major id"));

        if(file!=null){
            String s3FilePath = awsS3Service.uploadFileBucket(file);
            request.updateFileUrl(s3FilePath);
        }
        Post post = postRepository.save(request.toEntity(member, postType, postMajor));

        Team team = Team.builder()
                .leader_id(memberId)
                .post(post)
                .is_completed(false)
                .build();
        teamService.save(team);
        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .member(member)
                .build();
        teamMemberRepository.save(teamMember);
        return post;
    }

    public List<Post> findAllAble() {
        return postRepository.findAllAble();
    }

    public Post findById(Long id) {
        postRepository.incrementViewCountForActivePost(id);
        return postRepository.findByIdAble(id);
    }

    @Transactional
    public Post update(Long id, PostRequestDto request,MultipartFile file) {
        Post post = postRepository.findByIdAble(id);
        if(file != null){
            String s3FilePath = awsS3Service.uploadFileBucket(file);
            post.updateFileUrl(s3FilePath);
        }
        post.update(request.getContent());
        return post;
    }

    public void disablePostById(Long id) {
        postRepository.deleteById(id);
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

    public List<PostResponseDto> findAllScrap(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member doesn't exist"));
        List<Scrap> scrapList = scrapRepository.findAllByMember(member)
                .orElse(Collections.emptyList());

        return scrapList.stream()
                .map(scrap -> new PostResponseDto(scrap.getPost()))
                .collect(Collectors.toList());
    }

    public List<Post> findOnGoingPostAll() {
        return postRepository.findOnGoingPostAll();
    }

    public List<Post> findFinishPostAll() {
        return postRepository.findFinishPostAll();
    }

    public List<Post> findMyPosts(Long memberId){return postRepository.findAllByMemberId(memberId);}

    public void closePosts() {
        postRepository.closePosts();
    }
}
