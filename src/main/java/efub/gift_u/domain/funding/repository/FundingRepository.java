package efub.gift_u.domain.funding.repository;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.domain.FundingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {

    @Query("SELECT f FROM Funding f WHERE f.user.id = :userId")
    List<Funding> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Funding f WHERE f.user.id = :userId and f.status = :status")
    List<Funding> findAllByUserAndStatus(@Param("userId") Long userId, @Param("status") FundingStatus status);
}
