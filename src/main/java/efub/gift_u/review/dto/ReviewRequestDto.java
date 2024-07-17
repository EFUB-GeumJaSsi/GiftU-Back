package efub.gift_u.review.dto;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.review.domain.Review;
import efub.gift_u.user.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
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
