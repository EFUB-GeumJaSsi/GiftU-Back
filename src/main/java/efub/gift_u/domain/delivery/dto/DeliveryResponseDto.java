package efub.gift_u.domain.delivery.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryResponseDto {
    private String name;
    private String phoneNumber;
    private String addressNumber;
    private String addressDetail1;
    private String addressDetail2;

    @Builder
    public DeliveryResponseDto(String name, String phoneNumber, String addressNumber, String addressDetail1, String addressDetail2) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.addressNumber = addressNumber;
        this.addressDetail1 = addressDetail1;
        this.addressDetail2 = addressDetail2;
    }
}
