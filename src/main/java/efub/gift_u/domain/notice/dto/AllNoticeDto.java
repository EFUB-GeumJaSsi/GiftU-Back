package efub.gift_u.domain.notice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AllNoticeDto {

    private LocalDateTime now;
    private List<FundingDueNoticeDto> fundingDueDate;
    private List<FundingAchieveDto> fundingAchieve;
    private List<FriendNoticeDto> friendNotice;

    public AllNoticeDto(LocalDateTime now , List<FundingDueNoticeDto> fundingDueNoticeDtos , List<FundingAchieveDto> fundingAchieveDtos ,List<FriendNoticeDto> friend){
        this.now = now;
        this.fundingDueDate = fundingDueNoticeDtos;
        this.fundingAchieve = fundingAchieveDtos;
        this.friendNotice = friend;
    }

    public static AllNoticeDto from(LocalDateTime now ,List<FundingDueNoticeDto> fundingDueNoticeDto,List<FundingAchieveDto> fundingAchieveDtos, List<FriendNoticeDto> friendNoticeDtos){
         return new AllNoticeDto(
                 now,
                 fundingDueNoticeDto,
                 fundingAchieveDtos,
                 friendNoticeDtos
         );
    }

}
