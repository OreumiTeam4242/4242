package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import team.ftft.project4242.dto.ApplyResponseDto;

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

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // todo team 매핑하기!!

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="file_id")
    private UUID file_id;

    @Column(name="available_time")
    private String available_time;


    private String available_day;

    @Builder
    public Apply(Long apply_id, String title, String content, Post post, Member member, String available_time, String available_day) {
        this.apply_id = apply_id;
        this.title = title;
        this.content = content;
        this.post = post;
        this.member = member;
        this.available_time = available_time;
        this.available_day = available_day;
    }

    public ApplyResponseDto toResponse() {
        return ApplyResponseDto.builder()
                .title(title)
                .content(content)
                .available_time(available_time)
                .available_day(available_day)
                .build();
    }
}
