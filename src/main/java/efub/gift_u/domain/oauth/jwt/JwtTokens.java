package efub.gift_u.domain.oauth.jwt;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtTokens {
    private String accessToken;
    private String grantType;
    private Long expiresIn;
    private Boolean isNewUser;

    @Builder
    public JwtTokens(String accessToken, String grantType, Long expiresIn, Boolean isNewUser) {
        this.accessToken = accessToken;
        this.grantType = grantType;
        this.expiresIn = expiresIn;
        this.isNewUser = isNewUser;
    }

    public static JwtTokens of(String accessToken, String grantType, Long expiresIn, Boolean isNewUser) {
        return new JwtTokens(accessToken, grantType, expiresIn, isNewUser);
    }
}
