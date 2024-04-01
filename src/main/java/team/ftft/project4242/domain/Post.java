package team.ftft.project4242.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team.ftft.project4242.dto.CommentResponseDto;
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

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "major_id")
    private PostMajor postMajor;

    @ManyToOne(cascade = CascadeType.ALL)
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

    @Column(name="file_url")
    private String file_url;

    @Column(name="member_cnt")
    private Integer member_cnt;

    @Column(name="process_type")
    private String process_type;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date start_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @Column(name="end_date")
    private Date end_date;

    // 팀 생성을 위한 post, team 매핑 - 현진
    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private Long viewCount;

    @Builder
    public Post(String title, String content, PostType postType, PostMajor postMajor,Team team,Member member,Date start_date,Date end_date,Integer member_cnt,String process_type,String file_url) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        this.postType = postType;
        this.postMajor = postMajor;
        this.is_closed = false;
        this.use_yn = true;
        this.team = team;
        this.member = member;
        this.start_date = start_date;
        this.end_date = end_date;
        this.member_cnt = member_cnt;
        this.process_type = process_type;
        this.viewCount = 0L;
        this.file_url = file_url;
    }

    public PostResponseDto toResponse() {
        return PostResponseDto.builder()
                .commentList(commentList.stream().map(CommentResponseDto::new).toList())
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .start_date(start_date)
                .end_date(end_date)
                .major_id(postMajor.getMajor_id())
                .type_id(postType.getType_id())
                .member_cnt(member_cnt)
                .process_type(process_type)
                .file_url(file_url)
                .build();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateFileUrl(String file_url){
        this.file_url = file_url;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }
}
