package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ImgFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id", updatable = false)
    private Long img_id;

    @Column(name="img_nm", nullable = false)
    private String img_nm;

    public ImgFile(String s3FilePath) {
        this.img_nm = s3FilePath;
    }

// TODO : 추후에 매핑

//    @Column(name="member_id")
//    private String member_id;
}
