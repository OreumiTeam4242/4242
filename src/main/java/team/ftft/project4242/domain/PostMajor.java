package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "postMajor", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<Post>();

    @Builder
    public PostMajor(String major_nm) {
        this.major_nm = major_nm;
    }
}

