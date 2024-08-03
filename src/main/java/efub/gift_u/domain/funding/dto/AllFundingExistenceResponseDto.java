package efub.gift_u.domain.funding.dto;

import lombok.Getter;
import java.time.LocalDate;
import java.util.Map;

@Getter
public class AllFundingExistenceResponseDto {
    private Map<LocalDate, Boolean> ExistenceOfFundingOnDate;

    public AllFundingExistenceResponseDto(Map<LocalDate, Boolean> ExistenceOfFundingOnDate) {
        this.ExistenceOfFundingOnDate = ExistenceOfFundingOnDate;
    }
}
