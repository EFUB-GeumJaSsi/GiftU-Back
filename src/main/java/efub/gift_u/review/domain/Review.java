package efub.gift_u.review.domain;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "fundingId", updatable = false, nullable = false)
    private Funding funding;

    @Column(nullable = false)
    private String reviewTitle;

    @Column(nullable = false)
    private String reviewContent;

    public Review(Funding funding, String reviewTitle, String reviewContent) {
        this.funding = funding;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
    }
}
