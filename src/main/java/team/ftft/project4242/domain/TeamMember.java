package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamMemberId", updatable = false)
    private Long teamMemberId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    public TeamMember(Member member, Team team) {
        this.member = member;
        this.team = team;
    }

    public TeamMember toResponse() {
        return TeamMember.builder()
                .member(member)
                .team(team)
                .build();
    }
}