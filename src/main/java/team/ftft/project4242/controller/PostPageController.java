package team.ftft.project4242.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.dto.MemberResponseDto;
import team.ftft.project4242.dto.PostResponseDto;
import team.ftft.project4242.service.PostService;

import java.util.List;


@Controller
public class PostPageController {

    private final PostService postService;

    public PostPageController(PostService postService) {
        this.postService = postService;
    }

    //    모집글 생성
    @GetMapping("/page/post-form")
    public String applyPost(){
        return "post-form";
    }

    //    모집글 상세 조회
    @GetMapping("/page/post/{id}")
    public String postDetail(@PathVariable Long id, Model model){
        Post post = postService.findById(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        model.addAttribute("post", postResponseDto);

        return "recruitPostDetail";
    }

    // 메인 페이지 조회
    @GetMapping("/page/main")
    public String showMain(Model model) {
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

        return "main";
    }
}
