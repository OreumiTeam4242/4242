package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", updatable = false)
    private Long team_id;

    @Column(name="is_completed")
    private boolean is_completed;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "team")
    private List<Team_Member> teamMemberList = new ArrayList<Team_Member>();

    @Column(name="leader_id")
    private String leader_id;

//    @ManyToOne
//    @JoinColumn(name="member_id")
//    private Member member;
}
