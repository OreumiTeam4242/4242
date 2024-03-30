package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team.ftft.project4242.dto.AddTeamResponseDto;
import team.ftft.project4242.dto.PostTeamRequestDto;

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

    // toEntity()를 위한 Column 추가 - 현진
    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;
    //

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "team")
    private List<Team_Member> teamMemberList = new ArrayList<Team_Member>();

    @Column(name="leader_id")
    private String leader_id;

    @Column(name="use_yn")
    private boolean use_yn;

    @Builder
    public Team(boolean is_completed, String leader_id, boolean use_yn, Post post, Member member, String title, String content) {
        this.is_completed = is_completed;
        this.leader_id = leader_id;
        this.use_yn = use_yn;
        this.post = post;
        this.member = member;

        this.title = title;
        this.content = content;

    }

    public Team() {
    }

    public AddTeamResponseDto toResponse() {
        return AddTeamResponseDto.builder()
                .is_completed(is_completed)
                .leader_id(leader_id)
                .use_yn(use_yn)
                .post(post)
                .member(member)
                .build();
    }

    public void updateIscompleted(boolean is_completed) {
        this.is_completed = is_completed;
    }

}
