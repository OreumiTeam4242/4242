package team.ftft.project4242.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.dto.ApplyRequestDto;
import team.ftft.project4242.dto.ApplyResponseDto;
import team.ftft.project4242.service.ApplyService;

@Controller
public class ApplyPageController {

    private final ApplyService applyService;

    @Autowired
    public ApplyPageController(ApplyService applyService) {
        this.applyService = applyService;
    }

    // 신청글 생성 처리
    @PostMapping("/page/post/{postId}/apply")
    public String processApplyForm(@PathVariable Long postId
                                    ,Model model) {
        model.addAttribute(postId);
        return "register_post_detail";
    }

    //    신청글 생성
    @GetMapping("/page/apply-form")
    public String applyForm() {
        return "apply-form";
    }

    //    신청글 목록 조회
    @GetMapping("/page/register_list")
    public String applyList() {
        return "register_list";
    }

    //    신청글 상세 조회
    @GetMapping("/page/register_post_detail")
    public String applyDetail() {
        return "register_post_detail";
    }

}