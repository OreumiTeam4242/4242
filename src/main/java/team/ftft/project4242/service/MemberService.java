package team.ftft.project4242.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.dto.AddMemberRequest;
import team.ftft.project4242.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member registerMember(AddMemberRequest request) {
        // 이메일 중복 확인
        if (memberRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // DTO에서 엔터티로 변환
        Member member = request.toEntity();

        // 사용 여부 초기화
        member.setUse_yn(true);

        // 생성 시간 및 업데이트 시간 설정
        LocalDateTime now = LocalDateTime.now();
        member.setCreatedAt(now);
        member.setUpdatedAt(now);

        // 이미지 ID 설정 (임시로 UUID 생성)
        member.setImg_id(UUID.randomUUID());

        return memberRepository.save(member);
    }

    public boolean isEmailExists(String email) {
        return memberRepository.findByEmail(email) != null;
    }

    public Member findMemberByEmailAndPassword(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }
}
