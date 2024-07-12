package efub.gift_u.oauth.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    흐름
    HttpServletRequest 에서 Token을 획득
    JwtTokenProvider 을 통해 토큰 검증
    검증에 성공한 경우, JWT로 부터 추출한 memberId으로 UserAuthentication 객체 생성
    생성한 Authentication을 SecurityContextHolder에 저장
    나머지 FilterChain들을 수행할 수 있도록 doFilter(request,response)를 호출
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtService.getTokenFromRequest(request);
        System.out.println("JwtAuthenticationFilter 작동 됨 \n 액세스 토큰 : " + accessToken); // 확인 출력문
        System.out.println();

        if (accessToken != null && jwtService.validateToken(accessToken) == null) {
            String userId = jwtService.extractSubject(accessToken);
            // userId로 authentication 객체 생성 -> principal에 유저 정보 담음
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 생성한 authentication을 SecurityContextHolder에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 다음 필터로 요청 전달 (나머지 FilterChain들을 수행할 수 있도록 doFilter(request, response) 호출
        filterChain.doFilter(request, response);
    }

}
