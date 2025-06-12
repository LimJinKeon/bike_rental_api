package bike_rental_api.a.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        if (email == null && oAuth2User.getAttributes().containsKey("google_account")) {
            // Google OAuth2 응답에서 이메일을 올바르게 추출하는 로직 (기존과 동일)
            Object googleAccount = oAuth2User.getAttributes().get("google_account");
            if (googleAccount instanceof Map) {
                email = ((Map<String, Object>) googleAccount).get("email").toString();
            } else if (oAuth2User.getAttributes().containsKey("email")) { // 최상위 email 속성도 다시 확인
                email = oAuth2User.getAttribute("email");
            }
        }
        // 이메일이 여전히 null이면 에러 처리
        if (email == null) {
            log.error("OAuth2 로그인 성공 후 이메일을 찾을 수 없습니다.");
            response.sendRedirect("/error?message=email_not_found"); // 적절한 에러 페이지로 리다이렉션
            return;
        }

        String jwtToken = jwtTokenProvider.createToken(email);
        log.info("OAuth2 로그인 성공: " + email);
        log.info("발급된 JWT: " + jwtToken);

        // JWT를 HttpOnly 쿠키로 설정
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true); // JS에서 접근 불가
        cookie.setSecure(false);  // 개발환경에서 false로 해둬야 브라우저가 전송함 (HTTPS 아니면 안 붙음)
        cookie.setPath("/");      // 전체 경로에 대해 쿠키 유효
        cookie.setMaxAge(3600);   // 1시간 (jwt.expiration과 일치시켜도 됨)
        response.addCookie(cookie);

        // 홈 페이지로 리디렉션
        response.sendRedirect("http://localhost:8080/info");
    }
}
