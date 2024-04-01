package team.ftft.project4242.service;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.ApplyRequestDto;
import team.ftft.project4242.repository.ApplyRepository;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.service.file.AwsS3Service;

import java.util.List;

@Service
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final PostRepository postRepository;
    private final AwsS3Service awsS3Service;
    public ApplyService(ApplyRepository applyRepository, PostRepository postRepository,AwsS3Service awsS3Service) {
        this.applyRepository = applyRepository;
        this.postRepository = postRepository;
        this.awsS3Service = awsS3Service;
    }

    // POST : 신청글 생성
    public Apply saveApply(ApplyRequestDto request, Long post_id, @Nullable MultipartFile file) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(()-> new IllegalArgumentException("not found id" + post_id));
        if(file != null){
            String s3FilePath = awsS3Service.uploadFileBucket(file);
            request.updateFileUrl(s3FilePath);
        }
        // todo member 바꾸기!!!!!!!
        return applyRepository.save(request.toEntity(post));
    }

    // GET : 신청글 목록 조회
    public List<Apply> findAllApply() {
        return applyRepository.findAll();
    }

    public Apply findById(Long apply_id) {
        return applyRepository.findById(apply_id)
                .orElseThrow(() -> new IllegalArgumentException("not found id" + apply_id));
    }


}


