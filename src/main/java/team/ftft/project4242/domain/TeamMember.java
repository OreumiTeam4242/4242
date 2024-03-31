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

    @Column(name = "leader_id")
    private Long leader_id;

    @Builder
    public TeamMember(Member member, Long leader_id) {
        this.member = member;
        this.leader_id = leader_id;
    }

    public TeamMember toResponse() {
        return TeamMember.builder()
                .member(member)
                .leader_id(leader_id)
                .build();
    }
}