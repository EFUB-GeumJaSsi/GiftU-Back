package efub.gift_u.domain.oauth.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import efub.gift_u.domain.oauth.jwt.JwtRefreshToken;

@Repository
public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, Long> {

    JwtRefreshToken findByUserId(Long userId);

    void deleteByRefreshToken(String refreshToken);
}
