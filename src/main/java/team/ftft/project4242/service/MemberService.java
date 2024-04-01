package team.ftft.project4242.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.dto.MemberRequestDto;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member registerMember(MemberRequestDto request) {
        // DTO에서 엔터티로 변환
        Member member = request.toEntity();
        return memberRepository.save(member);
    }

    public boolean isEmailExists(String email) {
        return memberRepository.findByEmail(email) != null;
    }

    public MemberResponseDto findById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member doesn't exist"));

        return member.toResponse();
    }

    public MemberResponseDto update(Long memberId, MemberRequestDto request){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("member doesn't exist"));
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
}
