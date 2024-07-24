package efub.gift_u.domain.gift.repository;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.gift.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {

   List<Gift> findAllByFunding(Funding funding);
}
