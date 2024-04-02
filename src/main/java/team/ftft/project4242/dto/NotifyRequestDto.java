package team.ftft.project4242.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Notify;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class NotifyRequestDto {
    private String title;
    private String content;
    private String notifyMemberName;
    private String file_url;

    public Notify toEntity(Member postMember,Member notifyMember) {
        return Notify.builder()
                .title(title)
                .content(content)
                .postMember(postMember)
                .notifyMember(notifyMember)
                .file_url(file_url)
                .build();
    }
    public void updateFileUrl(String file_url){
        this.file_url = file_url;
    }
}
