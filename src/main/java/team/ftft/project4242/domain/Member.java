package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long member_id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="nickname", nullable = false)
    private String nickname;

    @Column(name="use_yn")
    private boolean use_yn;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name="img_id")
    private UUID img_id;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<Post>();

    @OneToMany(mappedBy = "member")
    private List<Scrap> scrapList = new ArrayList<Scrap>();

    @OneToMany(mappedBy = "member")
    private List<Team> teamList = new ArrayList<Team>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<Comment>();

    @OneToMany(mappedBy = "member")
    private List<Team_Member> teamMemberList = new ArrayList<Team_Member>();

    @OneToOne
    @JoinColumn(name="permission_id")
    private Role role;
}
