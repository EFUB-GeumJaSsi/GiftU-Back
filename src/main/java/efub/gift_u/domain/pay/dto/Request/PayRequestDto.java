package efub.gift_u.domain.pay.dto.Request;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.pay.domain.Pay;
import efub.gift_u.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayRequestDto {

    Long fundingId;
    Long amount; // 결제 금액

}
