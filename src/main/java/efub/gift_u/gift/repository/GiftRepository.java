package efub.gift_u.gift.repository;

import efub.gift_u.gift.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
}
