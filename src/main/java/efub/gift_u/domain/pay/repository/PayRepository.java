package efub.gift_u.domain.pay.repository;

import efub.gift_u.domain.pay.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, String> {

    long countByPayIdContainingIgnoreCase(String payId);
}
