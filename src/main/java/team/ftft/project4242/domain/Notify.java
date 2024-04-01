package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import team.ftft.project4242.dto.NotifyResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id", updatable = false)
    private Long notify_id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name ="post_member_id")
    private Member postMember;

    @ManyToOne
    @JoinColumn(name="notify_member_id")
    private Member notifyMember;

    @Column(name="file_id")
    private UUID file_id;

    @Builder
    public Notify(String title, String content, Member postMember, Member notifyMember, UUID file_id) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.postMember = postMember;
        this.notifyMember = notifyMember;
        this.file_id = file_id;
    }

    public NotifyResponseDto toResponse() {
        return NotifyResponseDto.builder()
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}