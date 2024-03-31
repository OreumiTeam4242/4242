package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(mappedBy = "postType")
    private Post post;

    @Builder
    public PostType(String type_nm) {
        this.type_nm = type_nm;
    }
}

