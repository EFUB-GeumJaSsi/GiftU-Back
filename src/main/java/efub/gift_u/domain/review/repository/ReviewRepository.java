package efub.gift_u.domain.review.repository;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review  findByFunding(Funding funding);

    Boolean existsReviewByFunding(Funding funding);
}
