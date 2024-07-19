package efub.gift_u.domain.oauth;
import efub.gift_u.domain.oauth.errorHandler.CustomAccessDeniedHandler;
import efub.gift_u.domain.oauth.errorHandler.CustomJwtAuthenticationEntryPoint;
import efub.gift_u.domain.oauth.jwt.JwtAuthenticationFilter;
import efub.gift_u.domain.oauth.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    // 인증이 필요없는 URL 패턴 목록을 정의
    private static final String[] AUTH_WHITELIST = {
            "/api/oauth/kakao",
            "/fundings/{fundingId}"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http의 특정 보안 구성을 비활성화
        http
                .csrf(csrf -> csrf.disable()) //csrf 공격을 대비하기 위한 csrf 토큰 disable 하기
                .formLogin(formLogin -> formLogin.disable()) //form login 비활성화 jwt를 사용하고 있으므로 폼 기반 로그인은 필요하지 않다.
                .httpBasic(httpBasic -> httpBasic.disable())//http 기본 인증은 사용자 이름과 비밀번호를 평문으로 전송하기 때문에 보안적으로 취약, 기본 인증을 비활성화 하고 있음
                .sessionManagement(session -> { // 세션 사용 안함
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .exceptionHandling(exception ->
                {
                    exception.authenticationEntryPoint(customJwtAuthenticationEntryPoint); // 인증 실패 시
                    exception.accessDeniedHandler(customAccessDeniedHandler); // 접근 거부 시
                });

        http.authorizeHttpRequests(auth -> {
                    // 여기에 정의된 URL 패턴은 모든 사용자에게 접근 허용하고, 그외의 모든 요청은 인증으로 요구함
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                // JWT 인증 필터 추가 - UsernamePasswordAuthentication 클래스 앞에 jwtAuthenticationFilter를 등록
                .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}