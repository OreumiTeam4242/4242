package team.ftft.project4242.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import team.ftft.project4242.dto.CommentResponseDto;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE comment SET use_yn = false WHERE comment_id = ?")
@SQLRestriction("use_yn = true")

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long comment_id;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name="content")
    private String content;

    @Column(name="use_yn")
    private boolean use_yn;

    @ManyToOne
    @JoinColumn(name="post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Builder
    public Comment(String content, Post post, Member member) {
        this.createdAt = LocalDateTime.now();
        this.content = content;
        this.use_yn = true;
        this.post = post;
        this.member = member;
    }
    public CommentResponseDto toResponse(){
        return CommentResponseDto.builder()
                .content(content)
                .nickname(member.getNickname())
                .created_at(createdAt)
                .use_yn(use_yn)
                .build();
    }
}
