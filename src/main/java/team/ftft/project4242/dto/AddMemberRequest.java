package team.ftft.project4242.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.ftft.project4242.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddMemberRequest {
    private String email;
    private String password;

    public Member toEntity() {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        return member;
    }
}
