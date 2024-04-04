package team.ftft.project4242.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> commentList;

    private LocalDate start_date;
    private LocalDate end_date;
    private Integer member_cnt;
    private String process_type;
    private String file_url;
    private String type;
    private String major;
    private String nickname;

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
        
    }

}
