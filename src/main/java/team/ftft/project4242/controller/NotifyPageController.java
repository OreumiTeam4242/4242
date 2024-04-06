package team.ftft.project4242.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotifyPageController {
    //    신고글 생성
    @GetMapping("/page/notify-form")
    public String applyNotify(){
        return "notify-form";
    }

    //    신고글 상세 조회
    @GetMapping("/page/notify_detail")
    public String notifyDetail(){
        return "notify_post_detail";
    }

    //    신고글 목록 조회
    @GetMapping("/page/administrator_list")
    public String notifyList(){
        return "administrator_list";
    }
}
