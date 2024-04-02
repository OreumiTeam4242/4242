package team.ftft.project4242.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.ftft.project4242.domain.Member;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberRequestDto {
    private String email;
    private String password;
    private String nickname;

}
