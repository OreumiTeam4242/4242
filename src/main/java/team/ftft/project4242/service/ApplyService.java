package team.ftft.project4242.service;

import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.Team;
import team.ftft.project4242.dto.ApplyRequestDto;
import team.ftft.project4242.dto.ApplyResponseDto;
import team.ftft.project4242.repository.ApplyRepository;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.repository.TeamRepository;
import team.ftft.project4242.service.file.AwsS3Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final PostRepository postRepository;
    private final AwsS3Service awsS3Service;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    public ApplyService(ApplyRepository applyRepository, PostRepository postRepository, AwsS3Service awsS3Service, MemberRepository memberRepository, TeamRepository teamRepository) {
        this.applyRepository = applyRepository;
        this.postRepository = postRepository;
        this.awsS3Service = awsS3Service;
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    // POST : 신청글 생성
    public Apply saveApply(ApplyRequestDto request, Long post_id, @Nullable MultipartFile file,Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalArgumentException("not found id" + memberId));
        Post post = postRepository.findById(post_id)
                .orElseThrow(()-> new IllegalArgumentException("not found id" + post_id));
        if(file != null){
            String s3FilePath = awsS3Service.uploadFileBucket(file);
            request.updateFileUrl(s3FilePath);
        }
        return applyRepository.save(request.toEntity(post,member));
    }

//    기존 findAllApply 메서드
//    public Page<ApplyResponseDto> findAllApply(Long postId, Pageable pageable)
//    {
//        Page<Apply> applies = applyRepository.findAllByPostId(postId,pageable);
//        List<ApplyResponseDto> applyList = applies.stream()
//                .map(ApplyResponseDto::new)
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(applyList,pageable,applies.getTotalElements());
//    }

    // GET : 신청글 목록 조회, ApplyServiceTest 하며 수정
    public Page<ApplyResponseDto> findAllApply(Long postId, Pageable pageable)
    {
        Page<Apply> applies = applyRepository.findAllByPostId(postId,pageable);
        List<ApplyResponseDto> applyList = applies.stream()
                .map(apply -> {
                    return ApplyResponseDto.builder()
                            .id(apply.getApply_id())
                            .title(apply.getTitle())
                            .content(apply.getContent())
                            .nickname(apply.getMember() != null ? apply.getMember().getNickname() : null)
                            .createdAt(apply.getCreatedAt())
                            .available_time(apply.getAvailable_time())
                            .available_day(apply.getAvailable_day())
                            .file_url(apply.getFile_url())
                            .role(apply.getMember() != null ? apply.getMember().getRole().value() : null)
                            .postId(apply.getPost() != null ? apply.getPost().getPost_id() : null)
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(applyList, pageable, applies.getTotalElements());
    }


    public Apply findById(Long apply_id) {
        return applyRepository.findById(apply_id)
                .orElseThrow(() -> new IllegalArgumentException("not found id" + apply_id));
    }


}