package team.ftft.project4242.commons.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import team.ftft.project4242.commons.security.UserDetailService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private UserDetailService userDetailService;
    @Bean
    public WebSecurityCustomizer configure() {      // 스프링 시큐리티 기능 비활성화
        return web -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations()
                );
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->              // 인증, 인가 설정
                        auth.requestMatchers("/page/intro","/page/login","/page/join","api/members/register","/api/auth/login",
                                        "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
//                                .anyRequest().authenticated())
                                .requestMatchers("/api/admin/","/page/notify_post_detail/**","/api/members/{memberId}/disabled").hasRole("관리자")
                                .anyRequest().permitAll())
                .formLogin(auth -> auth.loginPage("/page/login")// 폼 기반 로그인 설정
                        .defaultSuccessUrl("/page/main"))
                .logout(auth -> auth.logoutSuccessUrl("/page/login") // 로그아웃 설정
                        .invalidateHttpSession(true))
                .build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(provider);
    }
}