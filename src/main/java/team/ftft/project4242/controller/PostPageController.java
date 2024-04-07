package team.ftft.project4242.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import team.ftft.project4242.commons.security.CustomUserDetails;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.service.MemberService;
import team.ftft.project4242.service.PostService;

import java.time.LocalDate;
import java.util.List;


@Controller
public class PostPageController {

    private final PostService postService;
    private final MemberService memberService;

    public PostPageController(PostService postService, MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }

    //    모집글 생성
    @GetMapping("/page/post-form")
    public String applyPost(){
        return "post-form";
    }

    // 모집글 상세 조회 뷰 컨트롤러 수정
    @GetMapping("/page/post/{id}")
    public String postDetail(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        Post post = postService.findById(id);
        Long memberId = customUserDetails.getMemberId();
        MemberResponseDto writer = memberService.findById(post.getMember().getMember_id());

        if (post != null) {
            PostResponseDto postResponseDto = new PostResponseDto(post);
            MemberResponseDto userInfo = memberService.findById(memberId);
            model.addAttribute("post", postResponseDto);
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("writer", writer);
            model.addAttribute("now", LocalDate.now());
        } else {
            // 모집글이 없을 경우 처리
        }
        return "recruitPostDetail";
    }

    // 메인 페이지 조회
    @GetMapping("/page/main")
    public String showMain(Model model,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<Post> postList = postService.findAllAble();
        List<PostResponseDto> allpostList = postList.stream()
                        .map(PostResponseDto::new)
                                .toList();
        model.addAttribute("postList", allpostList);

        List<Post> hotPostList = postService.findTop3PostsByViewCount();
        List<PostResponseDto> allhotPostList = hotPostList.stream()
                        .map(PostResponseDto::new)
                                .toList();
        model.addAttribute("hotPostList", allhotPostList);

        Long memberId = customUserDetails.getMemberId();
        MemberResponseDto userInfo =memberService.findById(memberId);
        model.addAttribute("userinfo",userInfo);

        return "main";
    }

}
