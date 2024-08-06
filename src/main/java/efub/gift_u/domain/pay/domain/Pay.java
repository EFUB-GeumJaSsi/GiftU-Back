package efub.gift_u.domain.pay.domain;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.user.domain.User;
import efub.gift_u.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pay extends BaseTimeEntity {

    @Id
    @Column(updatable = false)
    private String payId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "fundingId", nullable = false)
    private Funding funding;

    @Column(nullable = false)
    private Long amount;

    public Pay(String paymentId , User user , Funding funding , Long amount){
        this.payId = paymentId;
        this.user = user;
        this.funding = funding;
        this.amount = amount;
    }

    public static Pay toEntity( String payNumber ,User user , Funding funding , Long amount){
        return new Pay(
                payNumber,
                user ,
                funding ,
                amount
        );
    }

}
