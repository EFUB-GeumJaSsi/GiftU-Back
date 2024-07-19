package efub.gift_u.domain.notice.dto;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.domain.FundingStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingNoticeDto {

    private Long fundingId;
    private Long userId;
    private String fundingTitle;
    private LocalDate fundingEndDate;
    private FundingStatus status;

    public static FundingNoticeDto from(Funding funding){
        return new FundingNoticeDto(
                funding.getFundingId(),
                funding.getUser().getUserId(),
                funding.getFundingTitle(),
                funding.getFundingEndDate(),
                funding.getStatus()
        );
    }



}
