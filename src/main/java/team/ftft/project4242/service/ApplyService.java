package team.ftft.project4242.service;

import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.dto.AddApplyRequestDto;
import team.ftft.project4242.repository.ApplyRepository;

import java.util.List;

@Service
public class ApplyService {
    private final ApplyRepository applyRepository;
    public ApplyService(ApplyRepository applyRepository) {
        this.applyRepository = applyRepository;
    }

    // POST : 신청글 생성
    public Apply saveApply(AddApplyRequestDto request) {
        return applyRepository.save(request.toEntity());
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


