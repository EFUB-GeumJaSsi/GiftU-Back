package efub.gift_u.domain.funding.dto;

import efub.gift_u.domain.delivery.domain.Delivery;
import efub.gift_u.domain.gift.dto.GiftRequestDto;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.user.domain.User;
import efub.gift_u.domain.funding.domain.FundingStatus;
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
    private String name;
    private String phoneNumber;
    private String addressNumber;
    private String addressDetail1;
    private String addressDetail2;
    private Boolean visibility;
    private Long password;
    private List<GiftRequestDto> gifts;

    @Builder
    public FundingRequestDto(String fundingTitle, String fundingContent, LocalDate fundingEndDate, String name, String phoneNumber, String addressNumber, String addressDetail1, String addressDetail2, Boolean visibility, Long password, List<GiftRequestDto> gifts) {
        this.fundingTitle = fundingTitle;
        this.fundingContent = fundingContent;
        this.fundingEndDate = fundingEndDate;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.addressNumber = addressNumber;
        this.addressDetail1 = addressDetail1;
        this.addressDetail2 = addressDetail2;
        this.visibility = visibility;
        this.password = password;
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
                .delivery(new Delivery(name, phoneNumber, addressNumber, addressDetail1, addressDetail2))
                .visibility(this.visibility)
                .password(this.visibility ? null : this.password)
                .nowMoney(0L)
                .fundingImageUrl("")
                .build();
    }
}


