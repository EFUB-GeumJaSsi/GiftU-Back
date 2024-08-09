package efub.gift_u.domain.review.dto;

import efub.gift_u.domain.review.domain.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewResponseDto {
     private Long reviewId;
     private Long fundingId;
     private String reviewContent;
     private LocalDateTime created_at;

     public ReviewResponseDto(Long reviewId , Long fundingId , String reviewContent , LocalDateTime created_at){
         this.reviewId =reviewId;
         this.fundingId = fundingId;
         this.reviewContent = reviewContent;
         this.created_at = created_at;
     }

    public static ReviewResponseDto from(Review review){
         return new ReviewResponseDto(
                 review.getReviewId(),
                 review.getFunding().getFundingId(),
                 review.getReviewContent(),
                 review.getCreatedAt()
         );
    }

}
