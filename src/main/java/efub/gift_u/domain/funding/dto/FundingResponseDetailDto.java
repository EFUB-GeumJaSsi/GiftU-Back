package efub.gift_u.domain.funding.dto;


import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.domain.FundingStatus;
import efub.gift_u.domain.gift.dto.GiftResponseDto;
import efub.gift_u.domain.participation.dto.ParticipationResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingResponseDetailDto {

    private Long fundingId;
    private Long userId;
    private String nickname;
    private String fundingTitle;
    private String fundingContent;
    private LocalDate fundingEndDate;
    private FundingStatus status;
    private LocalDateTime createdAt;
    private Boolean visibility;
    private String password;
    private Long nowMoney;
    private String fundingImageUrl;
    private List<ParticipationResponseDto> contributers ;
    private boolean isExistedReview; // 선물 후기 존재 여부
    private List<GiftResponseDto> giftList; // 해당 펀딩의 선물 목록

    public FundingResponseDetailDto(Long fundingId , Long userId , String nickname , String fundingTitle,
                                    String fundingContent , LocalDate fundingEndDate , FundingStatus status,
                                    LocalDateTime createdAt , Boolean visibility , String password , Long nowMoney,
                                    String fundingImageUrl , List<ParticipationResponseDto> contributers ,
                                    boolean isExistedReview ,List<GiftResponseDto> giftList
                                    ){
        this.fundingId = fundingId;
        this.userId = userId;
        this.nickname = nickname;
        this.fundingTitle = fundingTitle;
        this. fundingContent = fundingContent;
        this.fundingEndDate = fundingEndDate;
        this.status = status;
        this.createdAt = createdAt;
        this.visibility = visibility;
        this.password = password;
        this.fundingImageUrl = fundingImageUrl;
        this.contributers = contributers;
        this.isExistedReview = isExistedReview;
        this.giftList = giftList;
    }



    public static FundingResponseDetailDto from(Funding funding, List<ParticipationResponseDto> participationDetail ,
                                                boolean isExistedReview , List<GiftResponseDto> giftList) {
        return new FundingResponseDetailDto(
                funding.getFundingId(),
                funding.getUser().getUserId(),
                funding.getUser().getNickname(),
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