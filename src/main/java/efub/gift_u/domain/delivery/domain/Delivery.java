package efub.gift_u.domain.delivery.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {
    private String name;
    private String phoneNumber;
    private String addressNumber;
    private String addressDetail1;
    private String addressDetail2;

    public Delivery(String name, String phoneNumber, String addressNumber, String addressDetail1, String addressDetail2) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.addressNumber = addressNumber;
        this.addressDetail1 = addressDetail1;
        this.addressDetail2 = addressDetail2;
    }
}