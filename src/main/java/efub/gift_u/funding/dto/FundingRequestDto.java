package efub.gift_u.funding.dto;

import efub.gift_u.gift.dto.GiftRequestDto;
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
}


