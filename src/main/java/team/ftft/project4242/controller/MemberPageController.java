package team.ftft.project4242.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberPageController {
    //    인트로 페이지
    @GetMapping("/page/intro")
    public String intro(){
        return "intro";
    }

    //    회원가입
    @GetMapping("/page/join")
    public String join(){
        return "join";
    }

    //    로그인
    @GetMapping("/page/login")
    public String login(){
        return "login";
    }

    //    개인정보 조회
    @GetMapping("/page/personal_page")
    public String showPersonalPage(){
        return "personal_page";
    }

    //    개인정보 수정
    @GetMapping("/page/personal_edit_page")
    public String editPersonalPage(){
        return "personal_edit_page";
    }
}
