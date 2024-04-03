package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;
import team.ftft.project4242.domain.PostMajor;
import team.ftft.project4242.domain.PostType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private Member member;
    private Long type_id;
    private Long major_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> commentList;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Integer member_cnt;
    private String process_type;
    private String file_url;

    public PostResponseDto(Post post) {
        id = post.getPost_id();
        title = post.getTitle();
        content = post.getContent();
        member = post.getMember();
        type_id = post.getPostType().getType_id();
        major_id = post.getPostMajor().getMajor_id();
        createdAt = post.getCreatedAt();
        updatedAt = post.getUpdatedAt();
        commentList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
        start_date = post.getStart_date();
        end_date = post.getEnd_date();
        member_cnt = post.getMember_cnt();
        process_type = post.getProcess_type();
        file_url = post.getFile_url();
    }

    public String getTypeNameById(Long type_id) {
        return type_id == 1 ? "스터디" : "프로젝트";
    }
}
