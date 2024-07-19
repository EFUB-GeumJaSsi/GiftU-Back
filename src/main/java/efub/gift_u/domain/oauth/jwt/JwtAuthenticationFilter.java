package efub.gift_u.domain.oauth.jwt;

import lombok.RequiredArgsConstructor;
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
    생성한 Authentication을 SecurityContext에 저장
    나머지 FilterChain들을 수행할 수 있도록 doFilter(request,response)를 호출
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT 받아옴
        String accessToken = jwtService.getTokenFromRequest(request);

        System.out.println("JwtAuthenticationFilter 작동됨 \n 액세스 토큰 : " + accessToken); // 확인 출력문
        System.out.println();

        // 유효성 검사 (유효한 토큰인지 확인)
        if (accessToken != null && jwtService.validateToken(accessToken)) {
            String userId = jwtService.extractSubject(accessToken);
            // userId로 authentication 객체 생성 -> principal에 유저 정보 담음
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 생성한 authentication을 SecurityContext에 저장
            // token이 인증된 상태를 유지하도록 context(맥락)을 유지
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 다음 필터로 요청 전달 호출
        // UsernamePasswordAuthenticationFilter 로 이동
        filterChain.doFilter(request, response);
    }

}
