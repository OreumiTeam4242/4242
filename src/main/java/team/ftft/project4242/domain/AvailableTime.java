package team.ftft.project4242.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class AvailableTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_id", updatable = false)
    private String time_id;

    @Column(name="time_nm")
    private String time_nm;

    @Column(name="apply_id")
    private String apply_id;
}
