package team.ftft.project4242.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team.ftft.project4242.dto.CommentResponseDto;
import team.ftft.project4242.dto.PostResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE post SET use_yn = false WHERE post_id = ?")
@SQLRestriction("use_yn = true")

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

    @ManyToOne
    @JoinColumn(name = "major_id")
    private PostMajor postMajor;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PostType postType;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<Comment>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Apply> applyList = new ArrayList<Apply>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Scrap> scrapList = new ArrayList<Scrap>();

    @Column(name="file_url")
    private String file_url;

    @Column(name="member_cnt")
    private Integer member_cnt;

    @Column(name="process_type")
    private String process_type;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate start_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @Column(name="end_date")
    private LocalDate end_date;

    @OneToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    private Long viewCount;

    @Builder
    public Post(Long post_id, String title, String content, PostType postType, PostMajor postMajor,boolean use_yn, Member member,Team team,LocalDate start_date,LocalDate end_date,Integer member_cnt,String process_type,String file_url) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        this.postType = postType;
        this.postMajor = postMajor;
        this.is_closed = false;
        this.use_yn = true;
        this.member = member;
        this.team = team;
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
                .id(post_id)
                .title(title)
                .content(content)
                .nickname(member.getNickname())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .start_date(start_date)
                .end_date(end_date)
                .major(postMajor.getMajor_nm())
                .type(postType.getType_nm())
                .member_cnt(member_cnt)
                .process_type(process_type)
                .file_url(file_url)
                .type_id(postType.getType_id())
                .major_id(postMajor.getMajor_id())
                .viewCount(viewCount)
                .build();
    }

    public void update( String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateFileUrl(String file_url){
        this.file_url = file_url;
    }

}