package team.ftft.project4242.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.service.ApplyService;
import team.ftft.project4242.service.PostService;


@Controller
public class ApplyPageController {

    private final ApplyService applyService;
    private final PostService postService;

    @Autowired
    public ApplyPageController(ApplyService applyService, PostService postService) {
        this.applyService = applyService;
        this.postService = postService;
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

    //    신청글 상세 조회
    @GetMapping("/page/register_post_detail")
    public String applyDetail() {
        return "register_post_detail";
    }

}