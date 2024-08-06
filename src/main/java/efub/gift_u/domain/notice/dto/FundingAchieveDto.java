package efub.gift_u.domain.notice.dto;

import efub.gift_u.domain.funding.domain.Funding;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingAchieveDto {
       private String tag;
       private Long fundingId;
       private String fundingTitle;
       private String fundingImageUrl;
       private double percent;
       private LocalDateTime lastParticipateTime;

       public FundingAchieveDto(String tag,Long fundingId , String fundingTitle ,String fundingImageUrl, double percent , LocalDateTime lastParticipateTime){
           this.tag = tag;
           this.fundingId = fundingId;
           this.fundingTitle = fundingTitle;
           this.fundingImageUrl = fundingImageUrl;
           this.percent = percent;
           this.lastParticipateTime = lastParticipateTime;
       }

       public static FundingAchieveDto from (Funding funding , double percent , LocalDateTime lastParticipateTime){
           return new FundingAchieveDto(
                   "fundingAchieve",
                        funding.getFundingId(),
                        funding.getFundingTitle(),
                         funding.getFundingImageUrl(),
                        percent,
                   lastParticipateTime
                   );
       }


}
