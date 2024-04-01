package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Post;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;
    private Long type_id;
    private Long major_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> commentList;
    private Date start_date;
    private Date end_date;
    private Integer member_cnt;
    private String process_type;

    public PostResponseDto(Post post) {
        title = post.getTitle();
        content = post.getContent();
        type_id = post.getPostType().getType_id();
        major_id = post.getPostMajor().getMajor_id();
        createdAt = post.getCreatedAt();
        updatedAt = post.getUpdatedAt();
        commentList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
        start_date = post.getStart_date();
        end_date = post.getEnd_date();
        member_cnt = post.getMember_cnt();
        process_type = post.getProcess_type();
    }
}
