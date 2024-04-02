package team.ftft.project4242.commons.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 로그인 실패 이유 출력 또는 로그에 남기기
        System.out.println("로그인 실패 이유: " + exception.getMessage());

        // 실패한 이유에 따라 적절한 처리를 추가할 수 있습니다.
        String errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = "잘못된 사용자 이름 또는 비밀번호입니다.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "계정이 비활성화되었습니다.";
        } else {
            errorMessage = "로그인에 실패하였습니다.";
        }

        // 에러 메시지를 포함하여 로그인 페이지로 리다이렉트하도록 설정합니다.
        response.sendRedirect("/page/login?error=" + URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString()));

    }

}
