package efub.gift_u.domain.notice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AllNoticeDto {

    private LocalDate date;
    private List<FundingNoticeDto> funding;
    private List<FriendNoticeDto> friend;

    public static AllNoticeDto from(LocalDate today, List<FundingNoticeDto> fundingNoticeDto, List<FriendNoticeDto> friendNoticeDtos){
         return new AllNoticeDto(
                 today,
                 fundingNoticeDto,
                 friendNoticeDtos
         );
    }

}
