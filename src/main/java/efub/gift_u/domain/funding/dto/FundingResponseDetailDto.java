package efub.gift_u.domain.funding.dto;


import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.domain.FundingStatus;
import efub.gift_u.domain.gift.dto.GiftResponseDto;
import efub.gift_u.domain.participation.dto.ParticipationResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingResponseDetailDto {

    private Long fundingId;
    private Long userId;
    private String fundingTitle;
    private String fundingContent;
    private LocalDate fundingEndDate;
    private FundingStatus status;
    private LocalDateTime createdAt;
    private Boolean visibility;
    private Long password;
    private Long nowMoney;
    private String fundingImageUrl;
    private List<ParticipationResponseDto> contributers ;
    private boolean isExistedReview; // 선물 후기 존재 여부
    private List<GiftResponseDto> giftList; // 해당 펀딩의 선물 목록

    public static FundingResponseDetailDto from(Funding funding, List<ParticipationResponseDto> participationDetail ,
                                                boolean isExistedReview , List<GiftResponseDto> giftList) {
        return new FundingResponseDetailDto(
                funding.getFundingId(),
                funding.getUser().getUserId(),
                funding.getFundingTitle(),
                funding.getFundingContent(),
                funding.getFundingEndDate(),
                funding.getStatus(),
                funding.getCreatedAt(),
                funding.getVisibility(),
                funding.getPassword(),
                funding.getNowMoney(),
                funding.getFundingImageUrl(),
                participationDetail,
                isExistedReview,
                giftList
        );
    }
}
