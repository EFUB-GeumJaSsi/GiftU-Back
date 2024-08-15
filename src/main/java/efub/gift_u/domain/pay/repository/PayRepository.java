package efub.gift_u.domain.pay.repository;

import efub.gift_u.domain.pay.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayRepository extends JpaRepository<Pay, String> {

    long countByPayIdContainingIgnoreCase(String payId);

    @Query("SELECT p.payId FROM Pay p where p.funding.fundingId =:fundingId AND  p.user.userId =:userId")
    String findByFundingIdAndUserId(@Param("fundingId") Long fundingId ,@Param("userId") Long userId);

    @Query("SELECT p.payId FROM Pay p WHERE p.funding.fundingId =:fundingId")
    List<String> findByFundingId(@Param("fundingId") Long fundingId);

}
