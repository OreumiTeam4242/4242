package team.ftft.project4242.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.dto.MemberRequestDto;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.repository.PostRepository;
import team.ftft.project4242.service.MemberService;
import team.ftft.project4242.dto.LoginRequest;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@RequestBody MemberRequestDto request) {
        if (memberService.isEmailExists(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        Member member = memberService.registerMember(request);
        MemberResponseDto responseDto = member.toResponse();
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody LoginRequest request) {

//        Member foundMember = memberService.findMemberByEmailAndPassword(request.getEmail(), request.getPassword());
//        if (foundMember != null) {
//            return ResponseEntity.ok("Login successful");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid credentials");
//        }
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable Long memberId) {
        MemberResponseDto response = memberService.findById(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/{memberId}/update")
    public ResponseEntity<MemberResponseDto> updateMemberInfo(
            @PathVariable Long memberId,
            @RequestBody MemberRequestDto request) {

            MemberResponseDto response = memberService.update(memberId,request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{memberId}/disabled")
    public ResponseEntity<MemberResponseDto> disable(@PathVariable("memberId") Long member_id){
        MemberResponseDto response = memberService.disabled(member_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/{memberId}/enable")
    public ResponseEntity<MemberResponseDto> enable(
            @PathVariable Long memberId) {
        MemberResponseDto response = memberService.enable(memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}