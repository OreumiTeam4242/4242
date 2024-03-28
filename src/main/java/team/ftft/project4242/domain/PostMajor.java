package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class PostMajor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id", updatable = false)
    private Long major_id;

    @Column(name="major_nm")
    private String major_nm;

    @Column(name="post_id")
    private String post_id;
}
