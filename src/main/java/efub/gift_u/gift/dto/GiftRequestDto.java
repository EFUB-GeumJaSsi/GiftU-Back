package efub.gift_u.gift.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GiftRequestDto {
    private String giftName;
    private Integer price;
    private String giftUrl;
    private String giftImageUrl;

    @Builder
    public GiftRequestDto(String giftName, Integer price, String giftUrl, String giftImageUrl) {
        this.giftName = giftName;
        this.price = price;
        this.giftUrl = giftUrl;
        this.giftImageUrl = giftImageUrl;
    }
}

