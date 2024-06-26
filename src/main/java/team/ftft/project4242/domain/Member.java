package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team.ftft.project4242.dto.MemberResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET use_yn = false WHERE member_id = ?")
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<Post>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Scrap> scrapList = new ArrayList<Scrap>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<Comment>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Apply> applyList = new ArrayList<Apply>();

    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMemberList = new ArrayList<TeamMember>();

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="post_count")
    private Integer postCount;

    @Column(name="is_suspended")
    private boolean is_suspended;

    @Column(name="suspended_until")
    private LocalDate suspendedUntil;

    @Builder
    public Member(Long member_id, String email, String password, String nickname, boolean use_yn, LocalDateTime createdAt, LocalDateTime updatedAt,String img_url,Role role) {
        this.member_id = member_id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.use_yn = use_yn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.img_url = img_url;
        this.role = role;
        this.postCount = 0;
        this.is_suspended = false;
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
                .role(role.value())
                .isAdmin(Objects.equals(role.value(), "관리자"))
                .build();
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }

    public void updateimageUrl(String img_url){
        this.img_url = img_url;
    }

    public void disabled(){
        this.is_suspended = true;
        this.suspendedUntil = LocalDate.now().plusDays(7);
    }

    public void enable() {
        this.is_suspended = false;
        this.suspendedUntil = null;
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
