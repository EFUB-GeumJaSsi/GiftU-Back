package efub.gift_u.domain.oauth.errorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* 인증되지 않은 사용자가 보호된 리소스에 접근하려할 때 에러 핸들러 */
// HTTP 응답 상태 코드 401(Unauthorized)
@Component
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // 1번 commence 메소드 : 표준 인증 예외 처리 - Spring Security의 표준 인증 예외를 처리
    // ex) 로그인X 사용자가 접근할 때
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException  {
        setResponse(response, "Unauthorized", authException.getMessage());
    }

    // 2번 commence 메소드 : 특정 예외 처리 - JWT 관련 예외를 구체적으로 처리
    // ex) JWT 토큰 만료된 경우
    public void commence(HttpServletRequest request, HttpServletResponse response, String error) throws IOException {
        if (error.equals("TOKEN_EXPIRED")) {
            setResponse(response, "Token Expired", "만료된 토큰입니다.");
        }
        else if (error.equals("INVALID_TOKEN")) {
            setResponse(response, "Invalid Token", "유효하지 않는 토큰입니다.");
        }
        else {
            setResponse(response, "Fail Authentication", "토큰 검증에 실패하였습니다.");
        }
    }

    private void setResponse(HttpServletResponse response, String error, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");  // 한글 깨짐 방지 인코딩 설정
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", error);
        errorDetails.put("message", message);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
