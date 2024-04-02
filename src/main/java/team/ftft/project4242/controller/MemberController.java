package team.ftft.project4242.controller;

import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.commons.security.UserDetailService;
import team.ftft.project4242.domain.*;
import team.ftft.project4242.dto.LoginRequestDto;
import team.ftft.project4242.dto.MemberRequestDto;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.service.MemberService;

import java.util.ArrayList;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final UserDetailService userDetailService;

    private final AuthenticationManager authenticationManager;

    public MemberController(MemberService memberService, UserDetailService userDetailService, AuthenticationManager authenticationManager) {
        this.memberService = memberService;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
    }

//    회원가입
    @PostMapping("/api/members/register")
    public ResponseEntity<?> registerMember(@RequestBody MemberRequestDto request) {
        if (memberService.isEmailExists(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists"+request.getEmail());
        }
        Member member = memberService.registerMember(request);
        MemberResponseDto responseDto = member.toResponse();
        return ResponseEntity.ok(responseDto);
    }

//    로그인
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
                                   @RequestBody LoginRequestDto loginRequestDto) {
        UserDetails userDetails = userDetailService.loadUserByUsername(loginRequestDto.getEmail());
        // 인증 객체 생성
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(userDetails, loginRequestDto.getPassword(), new ArrayList<>());
        try {
            authenticationManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid Id or password");
        }
        // SecurityContextHolder : Authentication을 감싸는 객체
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();
        session.setAttribute
                (HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setMaxAge(30000 * 60);
        response.addCookie(cookie);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
//    로그아웃
    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        // 클라이언트에게 쿠키 삭제 요청
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

//    개인정보 조회

    @GetMapping("/api/members")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getMemberId();
      
        MemberResponseDto response = memberService.findById(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

//    개인정보 수정
    @PutMapping("/api/members/update")
    public ResponseEntity<MemberResponseDto> updateMemberInfo(@RequestPart MemberRequestDto request,
                                                              @RequestPart(value="img",required = false) MultipartFile file
                                                                ,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getMemberId();
        MemberResponseDto response = memberService.update(memberId, request,file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    // 정지
    @PutMapping("/api/members/{memberId}/disabled")
    public ResponseEntity<MemberResponseDto> disable(@PathVariable("memberId") Long member_id) {
        MemberResponseDto response = memberService.disabled(member_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

//    신고 유저 정지 해제
    @PutMapping("/api/members/{memberId}/enable")
    public ResponseEntity<MemberResponseDto> enable(
            @PathVariable Long memberId) {
        MemberResponseDto response = memberService.enable(memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
    // 탈퇴
    @DeleteMapping("/api/members/delete")
    public ResponseEntity<?> disable(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        Long member_id = customUserDetails.getMemberId();
        memberService.delete(member_id);

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}