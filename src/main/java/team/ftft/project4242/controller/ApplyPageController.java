package team.ftft.project4242.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplyPageController {
//    신청 글 생성
    @GetMapping("/page/apply-form")
    public String apply(){
        return "apply-form";
    }
}
