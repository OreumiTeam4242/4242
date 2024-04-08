package team.ftft.project4242.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.dto.ApplyResponseDto;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.service.ApplyService;
import team.ftft.project4242.service.MemberService;
import team.ftft.project4242.service.PostService;
import java.time.LocalDate;

@Controller
public class ApplyPageController {

    private final ApplyService applyService;
    private final PostService postService;
    private final MemberService memberService;

    @Autowired
    public ApplyPageController(ApplyService applyService, PostService postService, MemberService memberService) {
        this.applyService = applyService;
        this.postService = postService;
        this.memberService = memberService;
    }

    // 신청글 생성
    @GetMapping("/page/post/{postId}/apply")
    public String processApplyForm(@PathVariable Long postId, Model model) {
        return "apply-form";
    }

    //    신청글 조회
    @GetMapping("/page/apply-form")
    public String applyForm() {
        return "register_post_detail";
    }

    //    신청글 목록 조회
    @GetMapping("/page/post/{postId}/apply-list")
    public String applyList(@PathVariable Long postId,
                            Model model,
                            @PageableDefault(size = 9, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PostResponseDto postResponseDto = postService.findById(postId).toResponse();
        model.addAttribute("applyList",applyService.findAllApply(postId,pageable));
        model.addAttribute("post",postResponseDto);
        return "register_list";
    }

    // 신청 글 목록 상세 조회
    @GetMapping("/page/apply/{apply_id}")
    public String applyDetail(@PathVariable Long apply_id, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        Apply apply = applyService.findById(apply_id);
        Long memberId = customUserDetails.getMemberId();
        MemberResponseDto writer = memberService.findById(apply.getMember().getMember_id());

        if (apply != null) {
            ApplyResponseDto applyResponseDto = new ApplyResponseDto(apply);
            MemberResponseDto userInfo = memberService.findById(memberId);

            model.addAttribute("apply", applyResponseDto);
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("writer", writer);
            model.addAttribute("now", LocalDate.now());
        }
        return "register_post_detail";

    }
}