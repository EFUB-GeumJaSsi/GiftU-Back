package efub.gift_u.domain.notice.dto;

import efub.gift_u.domain.funding.domain.Funding;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingAchieveDto {
       Long fundingId;
       String fundingTitle;
       double percent;

       public FundingAchieveDto(Long fundingId , String fundingTitle , double percent){
           this.fundingId = fundingId;
           this.fundingTitle = fundingTitle;
           this.percent = percent;
       }

       public static FundingAchieveDto from (Funding funding , double percent){
           return new FundingAchieveDto(
                        funding.getFundingId(),
                        funding.getFundingTitle(),
                        percent
                   );
       }


}
