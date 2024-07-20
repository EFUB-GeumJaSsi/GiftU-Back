package efub.gift_u.oauth.jwt;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refreshTokenId")
    private Long refreshTokenId;

    @Column
    private String refreshToken;

    @Column
    private Long userId;

    private Long expiresIn;

    @Builder
    public JwtRefreshToken(String refreshToken, Long userId, Long expiresIn) {
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.expiresIn = expiresIn;
    }

    public void updateJwtRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}