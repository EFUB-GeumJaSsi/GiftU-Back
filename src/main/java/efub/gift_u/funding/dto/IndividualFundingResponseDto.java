package efub.gift_u.funding.dto;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.funding.domain.FundingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualFundingResponseDto {
    private Long fundingId;
    private String launcherNickname;
    private String fundingTitle;
    private LocalDate fundingEndDate;
    private FundingStatus status;
    private String fundingImageUrl;

    public static IndividualFundingResponseDto from(Funding funding){
        return new IndividualFundingResponseDto(
                funding.getFundingId(),
                funding.getUser().getNickname(),
                funding.getFundingTitle(),
                funding.getFundingEndDate(),
                funding.getStatus(),
                funding.getFundingImageUrl()
        );
    }

}
