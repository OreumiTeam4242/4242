package team.ftft.project4242.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import team.ftft.project4242.domain.Notify;
import team.ftft.project4242.dto.NotifyResponseDto;
import team.ftft.project4242.service.NotifyService;

@Controller
public class NotifyPageController {
    private final NotifyService notifyService;

    public NotifyPageController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    //    신고글 생성
    @GetMapping("/page/notify-form")
    public String applyNotify(){
        return "notify-form";
    }

    //    신고글 상세 조회
    @GetMapping("/page/notify_post_detail/{notify_id}")
    public String notifyDetail(@PathVariable Long notify_id, Model model){
        Notify notify = notifyService.findById(notify_id);

        if(notify != null) {
            NotifyResponseDto notifyResponseDto = new NotifyResponseDto(notify);
            model.addAttribute("notify", notifyResponseDto);
        }

        return "notify_post_detail";
    }
  
    //    신고글 목록 조회
    @GetMapping("/page/admin")
    public String notifyList(Model model,
                             @PageableDefault(size = 9, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("notifyList",notifyService.findAllNotify(pageable));
        return "administrator_list";
    }

}
