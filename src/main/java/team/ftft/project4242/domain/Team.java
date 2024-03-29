package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.ftft.project4242.dto.AddTeamResponse;

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

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="leader_id")
    private String leader_id;

    @Column(name="use_yn")
    private boolean use_yn;

    @Builder
    public Team(boolean is_completed, String leader_id, boolean use_yn, Post post, Member member) {
        this.is_completed = is_completed;
        this.leader_id = leader_id;
        this.use_yn = use_yn;
        this.post = post;
        this.member = member;
    }

    public Team() {
    }

    public AddTeamResponse toResponse() {
        return AddTeamResponse.builder()
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
