package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id", updatable = false)
    private String scrap_id;

    @Column(name="post_id")
    private String post_id;

    @Column(name="member_id")
    private String member_id;
}
