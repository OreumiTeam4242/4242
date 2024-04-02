package team.ftft.project4242.service;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Notify;
import team.ftft.project4242.dto.NotifyRequestDto;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.NotifyRepository;
import team.ftft.project4242.service.file.AwsS3Service;

import java.util.List;

@Service
public class NotifyService {
    private final NotifyRepository notifyRepository;
    private final AwsS3Service awsS3Service;
    private final MemberRepository memberRepository;
    public NotifyService(NotifyRepository notifyRepository, AwsS3Service awsS3Service, MemberRepository memberRepository) {
        this.notifyRepository = notifyRepository;
        this.awsS3Service = awsS3Service;
        this.memberRepository = memberRepository;
    }

    // POST : 신고글 생성
    public Notify saveNotify(NotifyRequestDto request, @Nullable MultipartFile file,Long memberId) {
        Member postMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("not found id" + memberId));
        Member notifyMember = memberRepository.findByNickname(request.getNotifyMemberName())
                .orElseThrow(() -> new IllegalArgumentException("not found id" + memberId));

        if(file != null){
            String s3FilePath = awsS3Service.uploadFileBucket(file);
            request.updateFileUrl(s3FilePath);
        }
        return notifyRepository.save(request.toEntity(postMember,notifyMember));
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