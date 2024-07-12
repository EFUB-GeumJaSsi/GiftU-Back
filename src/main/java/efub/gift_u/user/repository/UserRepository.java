package efub.gift_u.user.repository;

import efub.gift_u.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailOrNickname(String email, String nickname);
}
