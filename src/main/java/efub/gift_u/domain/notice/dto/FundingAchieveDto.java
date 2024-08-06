package efub.gift_u.domain.notice.dto;

import efub.gift_u.domain.funding.domain.Funding;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingAchieveDto {
       Long fundingId;
       String fundingTitle;
       String fundingImageUrl;
       double percent;
       LocalDateTime lastParticipateTime;

       public FundingAchieveDto(Long fundingId , String fundingTitle ,String fundingImageUrl, double percent , LocalDateTime lastParticipateTime){
           this.fundingId = fundingId;
           this.fundingTitle = fundingTitle;
           this.fundingImageUrl = fundingImageUrl;
           this.percent = percent;
           this.lastParticipateTime = lastParticipateTime;
       }

       public static FundingAchieveDto from (Funding funding , double percent , LocalDateTime lastParticipateTime){
           return new FundingAchieveDto(
                        funding.getFundingId(),
                        funding.getFundingTitle(),
                         funding.getFundingImageUrl(),
                        percent,
                   lastParticipateTime
                   );
       }


}
