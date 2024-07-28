package efub.gift_u.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingAllNoticeDto {

    private LocalDateTime now;
    private List<FundingDueNoticeDto> fundingDueDate;
    private List<FundingAchieveDto> fundingAchieve;

    public FundingAllNoticeDto(LocalDateTime now , List<FundingDueNoticeDto> fundingDueDate , List<FundingAchieveDto> fundingAchieve){
        this.now = now;
        this.fundingDueDate = fundingDueDate;
        this.fundingAchieve = fundingAchieve;
    }

    public static FundingAllNoticeDto from(LocalDateTime now , List<FundingDueNoticeDto> fundingDueDate , List<FundingAchieveDto> fundingAchieve){
        return new FundingAllNoticeDto(
                now,
                fundingDueDate,
                fundingAchieve
        );
    }

    //비었는지 확인
    @JsonIgnore
    public boolean isEmpty() {
        return (fundingDueDate == null && fundingAchieve == null);
    }
}
