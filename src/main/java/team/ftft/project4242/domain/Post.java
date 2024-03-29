package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @OneToOne
    @JoinColumn(name = "major_id")
    private PostMajor postMajor;

    @OneToOne
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
}
