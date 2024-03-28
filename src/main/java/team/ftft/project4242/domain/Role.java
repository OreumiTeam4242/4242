package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", updatable = false)
    private Long permission_id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name="permission_status")
    private String permission_status;
}
