package efub.gift_u.oauth.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, Long> {

    JwtRefreshToken findByUserId(Long userId);

    void deleteByRefreshToken(String refreshToken);
}
