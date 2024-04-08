package team.ftft.project4242.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

import java.io.IOException;
import java.util.ArrayList;

@Tag(name = "회원 CUD,로그인/로그아웃,정지")
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
    @Operation(summary = "회원가입", description = "등록되지 않은 회원을 등록한다.")
    @PostMapping("/api/members/register")
    public ResponseEntity<?> registerMember(@RequestBody MemberRequestDto request) {
        if (memberService.isEmailExists(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists"+request.getEmail());
        }
        if (memberService.isNicknameExists(request.getNickname())) {
            return ResponseEntity.badRequest().body("Nickname already exists"+request.getNickname());
        }
        Member member = memberService.registerMember(request);
        MemberResponseDto responseDto = member.toResponse();
        return ResponseEntity.ok(responseDto);
    }

//    로그인
    @Operation(summary = "로그인", description = "로그인 페이지에서 수행되는 로그인 요청")
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
                                   @RequestBody LoginRequestDto loginRequestDto) throws IOException {
        UserDetails userDetails = userDetailService.loadUserByUsername(loginRequestDto.getEmail());
        // 인증 객체 생성
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(userDetails, loginRequestDto.getPassword(), new ArrayList<>());

        if(!userDetails.isEnabled()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        try {
            authenticationManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
    @Operation(summary = "로그아웃", description = "로그인중인 회원 대상 로그아웃 기능")
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


//    개인정보 수정
    @Operation(summary = "개인정보 수정", description = "개인정보 수정 페이지에서의 개인정보 수정 기능 - 닉네임과 이미지를 바꿀 수 있다.")
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
    @Operation(summary = "신고 대상 정지", description = "신고글 상세 페이지에서 신고 대상 정지 처리(관리자 권한)")
    @PutMapping("/api/members/{memberId}/disabled")
    public ResponseEntity<MemberResponseDto> disable(@PathVariable("memberId") Long member_id) {
        MemberResponseDto response = memberService.disabled(member_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

//    신고 유저 정지 해제
    @Operation(summary = "신고 대상 정지 해제", description = "신고글 상세 페이지에서 신고 대상 정지 해제(관리자 권한)")
    @PutMapping("/api/members/{memberId}/enable")
    public ResponseEntity<MemberResponseDto> enable(
            @PathVariable Long memberId) {
        MemberResponseDto response = memberService.enable(memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
    // 탈퇴
    @Operation(summary = "회원 탈퇴", description = "탈퇴를 원하는 회원의 탈퇴")
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