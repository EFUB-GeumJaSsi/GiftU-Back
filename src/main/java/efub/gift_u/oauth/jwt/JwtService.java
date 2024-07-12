package efub.gift_u.oauth.jwt;

import efub.gift_u.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {

    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분

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
    public ErrorCode validateToken(String accessToken) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
            if (claims.getBody().getExpiration().before(new Date())) {
                return ErrorCode.FAIL_AUTHENTICATION; // 토큰 검증 실패
            }
            return null; // 토큰 검증 성공
        } catch (ExpiredJwtException e) {
            return ErrorCode.TOKEN_EXPIRED; // 토큰 만료
        } catch (Exception e) {
            return ErrorCode.FAIL_AUTHENTICATION; // 토큰 검증 실패
        }
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
