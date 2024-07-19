package efub.gift_u.funding.dto;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.funding.domain.FundingStatus;
import efub.gift_u.gift.dto.GiftRequestDto;
import efub.gift_u.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingRequestDto {
    private String fundingTitle;
    private String fundingContent;
    private LocalDate fundingEndDate;
    private String deliveryAddress;
    private Boolean visibility;
    private Long password;
    private String fundingImageUrl;
    private List<GiftRequestDto> gifts;

    @Builder
    public FundingRequestDto(String fundingTitle, String fundingContent, LocalDate fundingEndDate, String deliveryAddress, Boolean visibility, Long password, String fundingImageUrl, List<GiftRequestDto> gifts) {
        this.fundingTitle = fundingTitle;
        this.fundingContent = fundingContent;
        this.fundingEndDate = fundingEndDate;
        this.deliveryAddress = deliveryAddress;
        this.visibility = visibility;
        this.password = password;
        this.fundingImageUrl = fundingImageUrl;
        this.gifts = gifts;
    }

    public Funding toEntity(User user) {
        return Funding.builder()
                .user(user)
                .fundingTitle(this.fundingTitle)
                .fundingContent(this.fundingContent)
                .fundingStartDate(LocalDate.now())
                .fundingEndDate(this.fundingEndDate)
                .status(FundingStatus.IN_PROGRESS)
                .deliveryAddress(this.deliveryAddress)
                .visibility(this.visibility)
                .password(this.visibility ? null : this.password)
                .nowMoney(0L)
                .fundingImageUrl(this.fundingImageUrl)
                .build();
    }
}

