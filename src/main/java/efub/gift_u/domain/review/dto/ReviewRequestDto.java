package efub.gift_u.domain.review.dto;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.review.domain.Review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequestDto {

    private String reviewContent;


    public static Review toEntity( Funding funding , ReviewRequestDto requestDto){
        return Review.builder()
                .funding(funding)
                .reviewContent(requestDto.getReviewContent())
                .build();
    }

}
