package efub.gift_u.domain.notice.dto;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.domain.FundingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingDueNoticeDto {

    private Long fundingId;
    private Long userId;
    private String fundingTitle;
    private String fundingImageUrl;
    private LocalDate fundingEndDate;
    private FundingStatus status;

    public  FundingDueNoticeDto (Long fundingId , Long userId , String fundingTitle , String fundingImageUrl ,LocalDate fundingEndDate , FundingStatus status ){
        this.fundingId = fundingId;
        this.userId = userId;
        this.fundingTitle = fundingTitle;
        this.fundingImageUrl = fundingImageUrl;
        this.fundingEndDate = fundingEndDate;
        this.status = status;
    }

    public static FundingDueNoticeDto from(Funding funding){
        return new FundingDueNoticeDto(
                funding.getFundingId(),
                funding.getUser().getUserId(),
                funding.getFundingTitle(),
                funding.getFundingImageUrl(),
                funding.getFundingEndDate(),
                funding.getStatus()
        );
    }



}
