package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private String type;
    private String major;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> commentList;

    private LocalDate start_date;
    private LocalDate end_date;
    private Integer member_cnt;
    private String process_type;
    private String file_url;
    private String nickname;
    private Long viewCount;
    private Long type_id;
    private Long major_id;

    private Long type_id;
    private Long major_id;

    public PostResponseDto(Post post) {
        id = post.getPost_id();
        title = post.getTitle();
        content = post.getContent();
        type = post.getPostType().getType_nm();
        major = post.getPostMajor().getMajor_nm();
        createdAt = post.getCreatedAt();
        updatedAt = post.getUpdatedAt();
        commentList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
        start_date = post.getStart_date();
        end_date = post.getEnd_date();
        member_cnt = post.getMember_cnt();
        process_type = post.getProcess_type();
        file_url = post.getFile_url();
        nickname = post.getMember().getNickname();
        viewCount = post.getViewCount();
        type_id = post.getPostType().getType_id();
        major_id = post.getPostMajor().getMajor_id();
    }

    public String getTypeNameById(Long type_id) {
        return type_id == 1 ? "스터디" : "프로젝트";
    }

}
