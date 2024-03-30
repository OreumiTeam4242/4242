package team.ftft.project4242.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.dto.AddMemberRequest;
import team.ftft.project4242.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member registerMember(AddMemberRequest request) {
        // DTO에서 엔터티로 변환
        Member member = request.toEntity();
        return memberRepository.save(member);
    }

    public boolean isEmailExists(String email) {
        return memberRepository.findByEmail(email) != null;
    }

    public Member findMemberByEmailAndPassword(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }
}
