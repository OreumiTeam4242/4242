package team.ftft.project4242.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import team.ftft.project4242.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private Long type_id;
    private Long major_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate start_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate end_date;
    private Integer member_cnt;
    private String process_type;
    private String file_url;
    public Post toEntity(Member member, Team team, PostType postType, PostMajor postMajor) {
        return Post.builder()
                .title(title)
                .content(content)
                .postType(postType)
                .postMajor(postMajor)
                .member(member)
                .team(team)
                .start_date(start_date)
                .end_date(end_date)
                .member_cnt(member_cnt)
                .process_type(process_type)
                .file_url(file_url)
                .build();
    }
    public void updateFileUrl(String file_url){
        this.file_url = file_url;
    }
}
