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
    private String permission_id;

    @Column(name="member_id", nullable = false)
    private String member_id;

    @Column(name="permission_status")
    private String permission_status;
}
