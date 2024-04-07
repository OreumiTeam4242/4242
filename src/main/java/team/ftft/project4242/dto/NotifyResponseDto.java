package team.ftft.project4242.dto;

import lombok.*;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.domain.Notify;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotifyResponseDto {

    // 신고글 아이디, 제목, 사유, 신고 유저, 작성 유저, 작성 유저 등급, 생성일시, [작성자], [신고유저], 파일 아이디
    private Long id;
    private String title;
    private String content;
    private Long notify_member_id;
    private String notify_member_nickname;
    private String post_member_nickname;
    private String post_member_role;
    private LocalDateTime createdAt;
    private String file_url;
    private String notifyNickname;
    private String postNickname;

    public NotifyResponseDto(Notify notify) {
        id = notify.getNotify_id();
        title = notify.getTitle();
        content = notify.getContent();
        notify_member_id = notify.getNotifyMember().getMember_id();
        notify_member_nickname = notify.getNotifyMember().getNickname();
        post_member_nickname = notify.getPostMember().getNickname();
        post_member_role = String.valueOf(notify.getPostMember().getRole());
        createdAt = notify.getCreatedAt();
        file_url = notify.getFile_url();
        notifyNickname = notify.getNotifyMember().getNickname();
        postNickname = notify.getPostMember().getNickname();
    }
}
