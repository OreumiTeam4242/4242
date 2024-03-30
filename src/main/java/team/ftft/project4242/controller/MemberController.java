package team.ftft.project4242.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.dto.AddMemberRequest;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.repository.MemberRepository;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.service.MemberService;
import team.ftft.project4242.dto.LoginRequest;

import java.util.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@RequestBody AddMemberRequest request) {
        if (memberService.isEmailExists(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        Member member = memberService.registerMember(request);
        MemberResponseDto responseDto = member.toResponse();
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody LoginRequest request) {

        Member foundMember = memberService.findMemberByEmailAndPassword(request.getEmail(), request.getPassword());
        if (foundMember != null) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @GetMapping("/{member_id}")
    public ResponseEntity<?> getMemberInfo(@PathVariable Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);

        if (!memberOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Member member = memberOptional.get();

        Map<String, Object> response = new HashMap<>();
        response.put("nickname", member.getNickname());
        response.put("email", member.getEmail());

        Role role = member.getRole();
        response.put("permissionStatus", (role != null) ? role.getPermission_status() : "뉴비");

        List<Map<String, Object>> groupsInfo = new ArrayList<>();
        for (Post post : member.getPostList()) {
            Map<String, Object> groupInfo = new HashMap<>();
            groupInfo.put("majorNm", (post.getPostMajor() != null) ? post.getPostMajor().getMajor_nm() : null);
            groupInfo.put("typeNm", (post.getPostType() != null) ? post.getPostType().getType_nm() : null);
            groupInfo.put("leaderId", (post.getTeam() != null) ? post.getTeam().getLeader_id() : null);
            groupsInfo.add(groupInfo);
        }

        response.put("posts", groupsInfo);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{member_id}/update")
    public ResponseEntity<?> updateMemberInfo(
            @PathVariable("member_id") Long memberId,
            @RequestBody Map<String, Object> updateInfo) {

        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (!memberOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Member member = memberOptional.get();

        String nickname = (String) updateInfo.get("nickname");
        UUID imgId = updateInfo.containsKey("img_id") ? UUID.fromString((String) updateInfo.get("img_id")) : null;

        member.update(nickname, imgId);

        // 수정된 멤버 정보 저장
        memberRepository.save(member);

        return ResponseEntity.ok().build();
    }
}