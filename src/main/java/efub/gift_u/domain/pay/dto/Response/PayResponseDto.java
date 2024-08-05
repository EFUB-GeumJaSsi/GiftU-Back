package efub.gift_u.domain.pay.dto.Response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayResponseDto {

    String paymentId;
    Long userId;
    Long fundingId;
    Long amount;

    public PayResponseDto(String paymentId , Long userId , Long fundingId , Long amount){
        this.paymentId = paymentId;
        this.userId = userId;
        this.fundingId = fundingId;
        this.amount = amount;
    }

    public  static PayResponseDto from(String paymentId , Long userId , Long fundingId , Long amount){
        return new PayResponseDto(
                paymentId,
                userId,
                fundingId,
                amount
        );
    }
}
