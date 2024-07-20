package efub.gift_u.domain.oauth.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokens {
    private String accessToken;
    private String grantType;
    private Long expiresIn;

    public static JwtTokens of(String accessToken, String grantType, Long expiresIn) {
        return new JwtTokens(accessToken, grantType, expiresIn);
    }
}
