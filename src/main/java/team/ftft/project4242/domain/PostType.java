package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class PostType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", updatable = false)
    private Long type_id;

    @Column(name="type_nm")
    private String type_nm;

    @Column(name="post_id")
    private String post_id;
}
