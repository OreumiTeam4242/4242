package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import team.ftft.project4242.dto.TeamResponseDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE team SET use_yn = false WHERE team_id = ?")
@SQLRestriction("use_yn = true")
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    private List<TeamMember> teamMemberList = new ArrayList<TeamMember>();

    @Column(name="leader_id")
    private Long leader_id;

    @Column(name="use_yn")
    private boolean use_yn;



    @Builder
    public Team(boolean is_completed, Long leader_id,Post post) {
        this.is_completed = is_completed;
        this.leader_id = leader_id;
        this.post = post;
        this.use_yn = true;
    }

    public Team() {
    }

    public TeamResponseDto toResponse() {
        return TeamResponseDto.builder()
                .type(post.getPostType().getType_nm())
                .major(post.getPostMajor().getMajor_nm())
                .leader(post.getMember().getNickname())
                .is_completed(is_completed)
                .use_yn(use_yn)
                .team_id(team_id)
                .build();
    }

    public void updateIscompleted() {
        this.is_completed = true;
    }

}
