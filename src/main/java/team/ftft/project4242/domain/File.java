package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", updatable = false)
    private String file_id;

    @Column(name="file_nm", nullable = false)
    private String file_nm;

    @Column(name="member_id")
    private String member_id;
}
