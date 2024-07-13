package efub.gift_u.funding.repository;

import efub.gift_u.funding.domain.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRepository extends JpaRepository<Funding, Long> {
}
