package team.ftft.project4242.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Notify;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class AddNotifyRequestDto {
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Member post_member;
    private Member notify_member;

    public Notify toEntity() {
        return Notify.builder()
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}
