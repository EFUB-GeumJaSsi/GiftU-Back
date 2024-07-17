package efub.gift_u.funding.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingPasswordDto {

    private Long password;
}
