package efub.gift_u.domain.review.dto;

import efub.gift_u.domain.review.domain.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewResponseDto {
     private long reviewId;
     private long fundingId;
     private String reviewContent;
     private LocalDateTime created_at;

    public static ReviewResponseDto from(Review review){
         return new ReviewResponseDto(
                 review.getReviewId(),
                 review.getFunding().getFundingId(),
                 review.getReviewContent(),
                 review.getCreatedAt()
         );
    }

}
