package efub.gift_u.domain.gift.repository;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.gift.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {

   List<Gift> findAllByFunding(Funding funding);
   @Query("select MAX(g.price) from Gift g where g.funding.fundingId =: fundingId")
   Long findMaxPriceByFundingId(@Param("fundingId") Long fundingId);
}
