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
public class AddApplyRequest {
    private String title;
    private String content;
    private Post post;
    private Member member;
    private String available_time;
    private String available_day;

    public Apply toEntity() {
        return Apply.builder()
                .title(title)
                .content(content)
                .post(post)
                .member(member)
                .available_time(available_time)
                .available_day(available_day)
                .build();
    }
}
