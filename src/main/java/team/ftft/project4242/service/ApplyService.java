package team.ftft.project4242.service;

import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.ApplyRequestDto;
import team.ftft.project4242.repository.ApplyRepository;
import team.ftft.project4242.repository.PostRepository;

import java.util.List;

@Service
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final PostRepository postRepository;
    public ApplyService(ApplyRepository applyRepository, PostRepository postRepository) {
        this.applyRepository = applyRepository;
        this.postRepository = postRepository;
    }

    // POST : 신청글 생성
    public Apply saveApply(ApplyRequestDto request, Long post_id) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(()-> new IllegalArgumentException("not found id" + post_id));
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


