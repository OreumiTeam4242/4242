package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import team.ftft.project4242.dto.AddNotifyResponseDto;

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
    private Member post_member;

    @ManyToOne
    @JoinColumn(name="notify_member_id")
    private Member notify_member;

    @Column(name="file_id")
    private UUID file_id;

    @Builder
    public Notify(String title, String content, LocalDateTime createdAt, Member post_member, Member notify_member) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.post_member = post_member;
        this.notify_member = notify_member;
    }

    public AddNotifyResponseDto toResponse() {
        return AddNotifyResponseDto.builder()
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}
