package team.ftft.project4242.service;

import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Notify;
import team.ftft.project4242.dto.AddNotifyRequestDto;
import team.ftft.project4242.repository.NotifyRepository;

import java.util.List;

@Service
public class NotifyService {
    private final NotifyRepository notifyRepository;
    public NotifyService(NotifyRepository notifyRepository) {
        this.notifyRepository = notifyRepository;
    }

    // POST : 신고글 생성
    public Notify saveNotify(AddNotifyRequestDto request) {
        return notifyRepository.save(request.toEntity());
    }

    // GET : 신청글 목록 조회
    public List<Notify> findAllNotify() {
        return notifyRepository.findAll();
    }

    public Notify findById(Long notify_id) {
        return notifyRepository.findById(notify_id)
                .orElseThrow(() -> new IllegalArgumentException("not found id" + notify_id));
    }
}
