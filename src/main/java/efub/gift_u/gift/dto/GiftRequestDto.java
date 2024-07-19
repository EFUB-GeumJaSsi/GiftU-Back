package efub.gift_u.gift.dto;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.gift.domain.Gift;
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

    public Gift toEntity(Funding funding, String giftImageUrl) {
        return Gift.builder()
                .funding(funding)
                .giftName(this.giftName)
                .price(Long.valueOf(this.price))
                .giftUrl(this.giftUrl)
                .giftImageUrl(giftImageUrl)
                .build();
    }
}

