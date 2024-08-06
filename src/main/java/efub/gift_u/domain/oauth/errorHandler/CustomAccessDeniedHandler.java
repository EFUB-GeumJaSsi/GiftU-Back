//package efub.gift_u.domain.oauth.errorHandler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//// 인증된 사용자가 권한 없는 리소스에 접근하려할 때 호출되는 핸들러
//// HTTP 응답 상태 코드를 403 (Forbidden)으로 설정하는 역할
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        setResponse(response, "Forbidden", accessDeniedException.getMessage());
//    }
//
//    private void setResponse(HttpServletResponse response, String error, String message) throws IOException {
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.setContentType("application/json");
//        Map<String, String> errorDetails = new HashMap<>();
//        errorDetails.put("error", error);
//        errorDetails.put("message", message);
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
//    }
//}
