package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import team.ftft.project4242.dto.AddApplyResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id", updatable = false)
    private Long apply_id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="file_id")
    private UUID file_id;

    @Column(name="available_time")
    private String available_time;

    @Column(name="available_day")
    private String available_day;

    @Builder
    public Apply(String title, String content, Post post, Member member, String available_time, String available_day) {
        this.title = title;
        this.content = content;
        this.post = post;
        this.member = member;
        this.available_time = available_time;
        this.available_day = available_day;
    }

    public AddApplyResponse toResponse() {
        return AddApplyResponse.builder()
                .title(title)
                .content(content)
                .post(post)
                .member(member)
                .available_time(available_time)
                .available_day(available_day)
                .build();
    }
}
