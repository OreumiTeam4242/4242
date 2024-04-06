package team.ftft.project4242.service;

import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Notify;
import team.ftft.project4242.dto.NotifyRequestDto;
import team.ftft.project4242.dto.NotifyResponseDto;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.NotifyRepository;
import team.ftft.project4242.service.file.AwsS3Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<NotifyResponseDto> findAllNotify(Pageable pageable) {
        Page<Notify> notifies = notifyRepository.findAll(pageable);
        List<NotifyResponseDto> notifyList = notifies.stream()
                .map(NotifyResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(notifyList,pageable,notifies.getTotalElements());
    }

    public Notify findById(Long notify_id) {
        return notifyRepository.findById(notify_id)
                .orElseThrow(() -> new IllegalArgumentException("not found id" + notify_id));
    }

    public boolean isMemberExists(String nickname){
        return memberRepository.findByNickname(nickname).isPresent();
    }
}