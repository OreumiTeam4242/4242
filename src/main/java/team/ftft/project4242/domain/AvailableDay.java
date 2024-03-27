package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class AvailableDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id", updatable = false)
    private String day_id;

    @Column(name="day_nm")
    private String day_nm;

    @Column(name="apply_id")
    private String apply_id;
}
