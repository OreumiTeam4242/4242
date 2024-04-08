package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import team.ftft.project4242.dto.ApplyResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@SQLDelete(sql = "UPDATE apply SET use_yn = false WHERE apply_id = ?")
@SQLRestriction("use_yn = true")
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(name="file_url")
    private String file_url;

    @Column(name="available_time")
    private String available_time;

    private String available_day;

    private Boolean use_yn;
    @Builder
    public Apply(Long apply_id,String title, String content, Post post, Member member, String available_time, String available_day,String file_url) {
        this.apply_id = apply_id;
        this.title = title;
        this.content = content;
        this.post = post;
        this.member = member;
        this.available_time = available_time;
        this.available_day = available_day;
        this.createdAt = LocalDateTime.now();
        this.file_url = file_url;
        this.use_yn = true;
    }

    public ApplyResponseDto toResponse() {
        return ApplyResponseDto.builder()
                .id(apply_id)
                .title(title)
                .content(content)
                .nickname(member.getNickname())
                .createdAt(createdAt)
                .available_time(available_time)
                .available_day(available_day)
                .file_url(file_url)
                .nickname(member.getNickname())
                .role(member.getRole().value())
                .build();
    }
}
