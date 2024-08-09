package efub.gift_u.domain.search.dto;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.domain.FundingStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchResponseDto {

    private Long fundingId;
    private String fundingTitle;
    private String userNickname;
    private LocalDate fundingEndDate;
    private FundingStatus status;
    private String fundingImageUrl;

    public static SearchResponseDto from(Funding funding){
        return new SearchResponseDto(
                funding.getFundingId(),
                funding.getFundingTitle(),
                funding.getUser().getNickname(),
                funding.getFundingEndDate(),
                funding.getStatus(),
                funding.getFundingImageUrl()
        );
    }
}
