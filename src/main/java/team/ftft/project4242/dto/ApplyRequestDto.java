package team.ftft.project4242.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.ftft.project4242.domain.Apply;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Post;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ApplyRequestDto {
    private String title;
    private String content;
    private String available_time;
    private String available_day;
    private Member member;
    private String file_url;

    public Apply toEntity(Post post) {
        return Apply.builder()
                .title(title)
                .content(content)
                .post(post)
                .member(member)
                .available_time(available_time)
                .available_day(available_day)
                .file_url(file_url)
                .build();
    }

    public void updateFileUrl(String file_url){
        this.file_url = file_url;
    }
}
