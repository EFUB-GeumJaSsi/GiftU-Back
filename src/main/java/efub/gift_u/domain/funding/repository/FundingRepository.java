package efub.gift_u.domain.funding.repository;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.domain.FundingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {



    @Query("SELECT f FROM Funding f WHERE f.user.userId = :userId ORDER BY f.createdAt DESC") // 최신순으로 정렬 (=개설일 역순)
    List<Funding> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Funding f WHERE f.user.userId = :userId and f.status = :status ORDER BY f.createdAt DESC") // 최신순으로 정렬 (=개설일 역순)
    List<Funding> findAllByUserAndStatus(@Param("userId") Long userId, @Param("status") FundingStatus status);

    @Query("SELECT f FROM Funding f WHERE f.user.userId = :userId and f.fundingEndDate = :fundingEndDate")
    List<Funding> findAllByUserAndFundingEndDate(@Param("userId")Long userId, @Param("fundingEndDate") LocalDate fundingEndDate);

    // 검색
    @Query("SELECT f FROM Funding f where f.user.nickname LIKE %:searchWord% OR f.fundingTitle LIKE %:searchWord% OR f.fundingContent LIKE %:searchWord%")
    List<Funding> fundByUserOrFundingTitleOrFundingContent(@Param("searchWord") String searchWord);
}
