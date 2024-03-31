package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team.ftft.project4242.dto.PostResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long post_id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name="is_closed")
    private boolean is_closed;

    @Column(name="use_yn")
    private boolean use_yn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "major_id")
    private PostMajor postMajor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private PostType postType;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<Comment>();

    @OneToMany(mappedBy = "post")
    private List<Apply> applyList = new ArrayList<Apply>();

    @OneToMany(mappedBy = "post")
    private List<Scrap> scrapList = new ArrayList<Scrap>();

    @Column(name="file_id")
    private UUID file_id;

    @Column(name="member_cnt")
    private Integer member_cnt;

    @Column(name="process_type")
    private String process_type;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name="end_date")
    private Date end_date;

    // 팀 생성을 위한 post, team 매핑 - 현진
    @OneToOne
    @JoinColumn(name = "post_id")
    private Team team;

    @Column(name = "leader_id")
    private Long leader_id;

    public Post(String title, String content, int member_cnt, String postMajorName, String postTypeName, String process_type) {
        this.title = title;
        this.content = content;

        this.member_cnt = member_cnt;
        this.postMajor = new PostMajor(postMajorName);
        this.postType = new PostType(postTypeName);
        this.process_type = process_type;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        this.is_closed = false;
        this.use_yn = true;

    }

    public PostResponseDto toResponse() {
        return PostResponseDto.builder()
                .title(title)
                .content(content)
                .member_cnt(member_cnt)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .postType(postType)
                .postMajor(postMajor)
                .build();
    }

    public void update(String title, String content, LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }
}
