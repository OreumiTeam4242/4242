package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team.ftft.project4242.dto.MemberResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@SQLRestriction("use_yn = true")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long member_id;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="nickname")
    private String nickname;

    @Column(name="use_yn")
    private boolean use_yn;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name="img_url")
    private String img_url;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<Post>();

    @OneToMany(mappedBy = "member")
    private List<Scrap> scrapList = new ArrayList<Scrap>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<Comment>();

    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMemberList = new ArrayList<TeamMember>();

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="post_count")
    private Integer postCount;
    @Builder
    public Member(String email, String password, String nickname, boolean use_yn, LocalDateTime createdAt, LocalDateTime updatedAt,String img_url,Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.use_yn = use_yn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.img_url = img_url;
        this.role = role;
        this.postCount = 0;
    }
    public MemberResponseDto toResponse(){
        return MemberResponseDto
                .builder().
                member_id(member_id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .use_yn(use_yn)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .img_url(img_url)
                .build();
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }

    public void updateimageUrl(String img_url){
        this.img_url = img_url;
    }

    public void disabled(){
        this.use_yn = false;
    }

    public void enable() {
        this.use_yn = true;
    }
    public void increasePostCount(){
        this.postCount++;
    }
    public void checkAndUpgradeRole(){
        if(this.postCount >=2&&this.postCount<6){
            this.role = Role.ROLE_JUNIOR;
        } else if (this.postCount >=6) {
            this.role = Role.ROLE_SENIOR;
        }
    }
    public void deleteMember(){
        this.use_yn = false;
    }
}
