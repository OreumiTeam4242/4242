package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name="post_id")
    private String post_id;

    @Column(name="member_id")
    private String member_id;

    @Column(name="leader_id")
    private String leader_id;
}
