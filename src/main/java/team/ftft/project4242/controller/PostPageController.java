package team.ftft.project4242.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostPageController {
    //    모집글 생성
    @GetMapping("/page/post-form")
    public String applyPost(){
        return "post-form";
    }

    //    모집글 상세 조회
    @GetMapping("/page/recruitPostDetail")
    public String postDetail(){
        return "recruitPostDetail";
    }

    //    메인페이지 조회
    @GetMapping("/page/main")
    public String showMain(){
        return "main";
    }
}
