package efub.gift_u.domain.gift.repository;

import efub.gift_u.domain.gift.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
}
