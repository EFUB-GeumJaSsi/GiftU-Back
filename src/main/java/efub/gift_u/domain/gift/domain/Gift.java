package efub.gift_u.domain.gift.domain;

import efub.gift_u.domain.funding.domain.Funding;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long giftId;

    @ManyToOne
    @JoinColumn(name = "fundingId", nullable = false)
    private Funding funding;

    @Column(nullable = false)
    private String giftName;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String giftUrl;

    @Column(nullable = false)
    private String giftImageUrl;

    @Builder
    public Gift(Funding funding, String giftName, Long price, String giftUrl, String giftImageUrl) {
        this.funding = funding;
        this.giftName = giftName;
        this.price = price;
        this.giftUrl = giftUrl;
        this.giftImageUrl = giftImageUrl;
    }
}
