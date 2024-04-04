package team.ftft.project4242.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.dto.TeamResponseDto;
import team.ftft.project4242.service.MemberService;
import team.ftft.project4242.service.PostService;
import team.ftft.project4242.service.TeamService;

import java.util.List;

@Controller
public class MemberPageController {

    private final MemberService memberService;
    private final PostService postService;
    private final TeamService teamService;

    public MemberPageController(MemberService memberService, PostService postService, TeamService teamService) {
        this.memberService = memberService;
        this.postService = postService;
        this.teamService = teamService;
    }

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
    @GetMapping("/page/my_page")
    public String showPersonalPage(@AuthenticationPrincipal CustomUserDetails customUserDetails
                                    , Model model){
//        Long memberId = customUserDetails.getMemberId();
        Long memberId = 18L;
        // userInfo
        MemberResponseDto userInfo = memberService.findById(memberId);
        //진행중인 스터디/프로젝트 팀
        List<TeamResponseDto> onGoingTeam = teamService.findOnGoingTeamAll(memberId);
        for(TeamResponseDto team : onGoingTeam){
            System.out.println(team.getTeam_id());
        }
        // 내가 쓴 post
        List<Post> myPostList = postService.findMyPosts(memberId);
        List<PostResponseDto> myList = myPostList.stream()
                .map(PostResponseDto::new)
                .toList();
        // 스크랩한 글
        List<PostResponseDto> scrapList  = postService.findAllScrap(memberId);
        //종료된 스터디 팀
        List<TeamResponseDto> finishedTeam = teamService.findFinishedTeam(memberId);


        model.addAttribute("userInfo",userInfo);
        model.addAttribute("onGoingTeamList",onGoingTeam);
        model.addAttribute("myPostList",myList);
        model.addAttribute("scrapList",scrapList);
        model.addAttribute("finishedTeamList",finishedTeam);

        return "personal_page";
    }

    //    개인정보 수정
    @GetMapping("/page/my_edit_page")
    public String editPersonalPage(){
        return "personal_edit_page";
    }
}
