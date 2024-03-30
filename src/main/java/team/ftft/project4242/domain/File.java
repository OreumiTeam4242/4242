package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", updatable = false)
    private Long file_id;

    @Column(name="file_nm", nullable = false)
    private String file_nm;

    public File(String s3FilePath) {
        this.file_nm = s3FilePath;
    }

    // Todo : 멤버에 매핑하기..
//    @Column(name="member_id")
//    private String member_id;
}
