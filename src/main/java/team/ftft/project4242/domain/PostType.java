package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "postType")
    private List<Post> postList = new ArrayList<Post>();

    @Builder
    public PostType(String type_nm) {
        this.type_nm = type_nm;
    }
}

