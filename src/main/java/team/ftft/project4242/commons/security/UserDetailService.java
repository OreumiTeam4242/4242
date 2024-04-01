package team.ftft.project4242.commons.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.ftft.project4242.domain.Member;
import team.ftft.project4242.repository.MemberRepository;

@Service
public class UserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserDetailService(MemberRepository userRepository) {
        this.memberRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email not found : "+email));
        return new CustomUserDetails(member);
    }
}