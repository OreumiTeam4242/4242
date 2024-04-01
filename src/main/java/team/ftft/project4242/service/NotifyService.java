package team.ftft.project4242.service;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.Notify;
import team.ftft.project4242.dto.NotifyRequestDto;
import team.ftft.project4242.repository.NotifyRepository;
import team.ftft.project4242.service.file.AwsS3Service;

import java.util.List;

@Service
public class NotifyService {
    private final NotifyRepository notifyRepository;
    private final AwsS3Service awsS3Service;
    public NotifyService(NotifyRepository notifyRepository, AwsS3Service awsS3Service) {
        this.notifyRepository = notifyRepository;
        this.awsS3Service = awsS3Service;
    }

    // POST : 신고글 생성
    public Notify saveNotify(NotifyRequestDto request, @Nullable MultipartFile file) {
        if(file != null){
            String s3FilePath = awsS3Service.uploadFileBucket(file);
            request.updateFileUrl(s3FilePath);
        }
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
