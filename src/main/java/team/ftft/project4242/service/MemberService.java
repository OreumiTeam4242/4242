package team.ftft.project4242.service;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.dto.MemberRequestDto;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.service.file.AwsS3Service;

import java.time.LocalDateTime;

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
        System.out.println(memberId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member doesn't exist"));

        return member.toResponse();
    }

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

    public MemberResponseDto disabled(Long memberId){
        // TODO : 삭제가 아닌 use_yn value 변경
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member_id doesn't exist"));
        member.disabled();
        return member.toResponse();
    }

    public MemberResponseDto enable(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member_id doesn't exist"));
        member.enable();  // use_yn 값을 true로 변경
        return member.toResponse();
    }

    public MemberResponseDto delete(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member_id doesn't exist"));
        memberRepository.delete(member);
        return member.toResponse();
    }
}
