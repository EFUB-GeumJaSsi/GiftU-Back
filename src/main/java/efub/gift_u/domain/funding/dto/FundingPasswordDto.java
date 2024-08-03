package efub.gift_u.domain.funding.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingPasswordDto {

    private String password;
}