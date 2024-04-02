package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE team_member SET use_yn = false WHERE team_member_id = ?")
@SQLRestriction("use_yn = true")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamMemberId", updatable = false)
    private Long teamMemberId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean use_yn;

    @Builder
    public TeamMember(Member member, Team team) {
        this.member = member;
        this.team = team;
        this.use_yn = true;
    }

    public TeamMember toResponse() {
        return TeamMember.builder()
                .member(member)
                .team(team)
                .build();
    }
}