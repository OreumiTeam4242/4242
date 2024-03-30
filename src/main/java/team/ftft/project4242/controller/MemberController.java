package team.ftft.project4242.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.dto.AddMemberRequest;
import team.ftft.project4242.service.MemberService;
import team.ftft.project4242.dto.LoginRequest;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@RequestBody AddMemberRequest request) {
        if (memberService.isEmailExists(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        Member registeredMember = memberService.registerMember(request);
        return ResponseEntity.ok(registeredMember);
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
}
