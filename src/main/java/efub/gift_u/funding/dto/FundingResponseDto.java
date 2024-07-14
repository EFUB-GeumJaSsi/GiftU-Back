package efub.gift_u.funding.dto;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.funding.domain.FundingStatus;
import efub.gift_u.gift.dto.GiftResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingResponseDto {
    private Long fundingId;
    private Long userId;
    private String fundingTitle;
    private String fundingContent;
    private LocalDate fundingStartDate;
    private LocalDate fundingEndDate;
    private FundingStatus status;
    private LocalDateTime createdAt;
    private String deliveryAddress;
    private Boolean visibility;
    private Integer password;
    private Long nowMoney;
    private String fundingImageUrl;
    private List<GiftResponseDto> gifts;

    @Builder
    public FundingResponseDto(Long fundingId, Long userId, String fundingTitle, String fundingContent, LocalDate fundingStartDate, LocalDate fundingEndDate, FundingStatus status, LocalDateTime createdAt, String deliveryAddress, Boolean visibility, Integer password, Long nowMoney, String fundingImageUrl, List<GiftResponseDto> gifts) {
        this.fundingId = fundingId;
        this.userId = userId;
        this.fundingTitle = fundingTitle;
        this.fundingContent = fundingContent;
        this.fundingStartDate = fundingStartDate;
        this.fundingEndDate = fundingEndDate;
        this.status = status;
        this.createdAt = createdAt;
        this.deliveryAddress = deliveryAddress;
        this.visibility = visibility;
        this.password = password;
        this.nowMoney = nowMoney;
        this.fundingImageUrl = fundingImageUrl;
        this.gifts = gifts;
    }

    public static FundingResponseDto fromEntity(Funding funding) {
        List<GiftResponseDto> gifts = funding.getGiftList().stream()
                .map(GiftResponseDto::fromEntity)
                .collect(Collectors.toList());

        return FundingResponseDto.builder()
                .fundingId(funding.getFundingId())
                .userId(funding.getUser().getUserId())
                .fundingTitle(funding.getFundingTitle())
                .fundingContent(funding.getFundingContent())
                .fundingStartDate(funding.getFundingStartDate())
                .fundingEndDate(funding.getFundingEndDate())
                .status(funding.getStatus())
                .createdAt(funding.getCreatedAt())
                .deliveryAddress(funding.getDeliveryAddress())
                .visibility(funding.getVisibility())
                .password(funding.getPassword() != null ? Integer.parseInt(String.valueOf(funding.getPassword())) : null)
                .nowMoney(funding.getNowMoney())
                .fundingImageUrl(funding.getFundingImageUrl())
                .gifts(gifts)
                .build();
    }
}
