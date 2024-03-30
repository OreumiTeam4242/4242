package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team.ftft.project4242.dto.TeamResponseDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", updatable = false)
    private Long team_id;

    @Column(name="is_completed")
    private boolean is_completed;

    // 팀 생성을 위한 post, team 매핑 - 현진
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "team")
    private List<Team_Member> teamMemberList = new ArrayList<Team_Member>();

    @Column(name="leader_id")
    private Long leader_id;

    @Column(name="use_yn")
    private boolean use_yn;

    @Builder
    public Team(boolean is_completed, Post post, Member member) {
        this.is_completed = is_completed;
        this.leader_id = post.getLeader_id();
        this.use_yn = true;
        this.post = post;
        this.member = member;
    }

    public Team() {
    }

    public TeamResponseDto toResponse() {
        return TeamResponseDto.builder()
                .is_completed(is_completed)
                .use_yn(use_yn)
                .post(post)
                .member(member)
                .build();
    }

    public void updateIscompleted() {
        this.is_completed = false;
    }

}
