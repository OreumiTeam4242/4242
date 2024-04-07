package team.ftft.project4242.service;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.dto.MemberRequestDto;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.service.file.AwsS3Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static team.ftft.project4242.domain.Role.ROLE_NEW_BIE;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;
    private BCryptPasswordEncoder endcoder;

    public MemberService(MemberRepository memberRepository, AwsS3Service awsS3Service,BCryptPasswordEncoder endcoder) {
        this.memberRepository = memberRepository;
        this.awsS3Service = awsS3Service;
        this.endcoder = endcoder;
    }

    public Member registerMember(MemberRequestDto request) {
        Member member = Member.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(endcoder.encode(request.getPassword()))
                .img_url("https://eunji-web-bucket.s3.ap-northeast-2.amazonaws.com/4cb67e60-0bbb-4701-b248-2460e0082fab.jpg") // 기본 이미지 설정
                .use_yn(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(ROLE_NEW_BIE)
                .build();
        return memberRepository.save(member);
    }

    public boolean isEmailExists(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public boolean isNicknameExists(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }

    public MemberResponseDto findById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member doesn't exist"));

        return member.toResponse();
    }

    @Transactional
    public MemberResponseDto update(Long memberId, MemberRequestDto request, @Nullable MultipartFile file){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member doesn't exist"));
        if(file != null){
            String s3FilePath = awsS3Service.uploadImageBucket(file);
            member.updateimageUrl(s3FilePath);
        }
        member.update(request.getNickname());
        return member.toResponse();

    }

    @Transactional
    public MemberResponseDto disabled(Long memberId){
        // TODO : 삭제가 아닌 is_suspended value 변경
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member_id doesn't exist"));
        memberRepository.toggleSuspend(memberId);
        member.disabled();
        return member.toResponse();
    }

    @Transactional
    public MemberResponseDto enable(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member_id doesn't exist"));
        memberRepository.toggleSuspend(memberId);  // use_yn 값을 true로 변경
        member.enable();
        return member.toResponse();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkAndUnsuspendMembers() {
        LocalDate now = LocalDate.now();
        LocalDate sevenDaysLater = now.plusDays(7);

        // 7일 후가 되면 is_suspended 값을 false로 변경합니다.
        memberRepository.updateSuspendedMembers(sevenDaysLater);
    }

    public MemberResponseDto delete(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member_id doesn't exist"));
        memberRepository.delete(member);
        return member.toResponse();
    }
}
