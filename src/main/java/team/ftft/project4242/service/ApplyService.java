package team.ftft.project4242.service;

import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.dto.AddApplyRequest;
import team.ftft.project4242.repository.ApplyRepository;

@Service
public class ApplyService {
    private final ApplyRepository applyRepository;
    public ApplyService(ApplyRepository applyRepository) {
        this.applyRepository = applyRepository;
    }
    public Apply saveApply(AddApplyRequest request) {
        return applyRepository.save(request.toEntity());
    }
}
