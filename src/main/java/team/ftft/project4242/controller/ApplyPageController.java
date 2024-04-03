package team.ftft.project4242.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplyPageController {
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