package bike_rental_api.a.handler; // 적절한 패키지명으로 변경

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils; // 쿠키 유틸리티 사용

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // JwtTokenProvider는 필요에 따라 주입 가능 (JWT 유효성 검사 등)
    // private final JwtTokenProvider jwtTokenProvider;
    // public JwtAuthenticationEntryPoint(JwtTokenProvider jwtTokenProvider) {
    //     this.jwtTokenProvider = jwtTokenProvider;
    // }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.warn("JwtAuthenticationEntryPoint 호출됨. 요청 URI: {}", request.getRequestURI());
        log.warn("인증 예외: {}", authException.getMessage());

        Cookie jwtCookie = WebUtils.getCookie(request, "jwt");

        if (jwtCookie != null && jwtCookie.getValue() != null && !jwtCookie.getValue().isEmpty()) {
            log.info("JWT 쿠키 발견. /info로 리다이렉션합니다.");
            response.sendRedirect("/info");
        } else {
            // JWT 쿠키가 없거나 유효하지 않으면 기본 로그인 페이지로 리다이렉션
            log.info("JWT 쿠키 없음. 기본 로그인 페이지 '/login'으로 리다이렉션합니다.");
            response.sendRedirect("/login");
        }
    }
}