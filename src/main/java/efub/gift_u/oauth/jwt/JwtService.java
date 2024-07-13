package efub.gift_u.oauth.jwt;

import efub.gift_u.exception.ErrorCode;
import efub.gift_u.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 300; // 5시간

    private final Key key;

    public JwtService(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JwtTokens 생성
    public JwtTokens generateJwtToken(Long userId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String subject = userId.toString();
        String accessToken = generateToken(subject, accessTokenExpiredAt);

        return JwtTokens.of(accessToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME);
    }

    // 토큰 생성
    public String generateToken(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    // 토큰에서 subject(userId) 추출
    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }

    // subject 추출 후 Long 타입의 멤버 ID 반환
    public Long extractUserId(String accessToken) {
        return Long.valueOf(extractSubject(accessToken));
    }


    // 클레임 파싱
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰 검증 //
    public boolean validateToken(String accessToken) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date()); // 만료일자가 현재 날짜 이전인지 확인
        } catch (ExpiredJwtException e) {
            log.info(String.valueOf(ErrorCode.TOKEN_EXPIRED)); // 토큰 만료
        } catch (JwtException e) {
            log.info(String.valueOf(ErrorCode.INVALID_TOKEN)); // 유효하지 않는 토큰
        } catch (Exception e) {
            log.info(String.valueOf(ErrorCode.FAIL_AUTHENTICATION)); // 토큰 검증 실패
        }
        return false;
    }

    // 요청에서 토큰 추출하기 //
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
