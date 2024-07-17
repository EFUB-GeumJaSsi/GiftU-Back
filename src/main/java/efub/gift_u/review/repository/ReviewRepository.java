package efub.gift_u.review.repository;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.participation.domain.Participation;
import efub.gift_u.review.domain.Review;
import efub.gift_u.review.dto.ReviewResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review  findByFunding(Funding funding);
}
