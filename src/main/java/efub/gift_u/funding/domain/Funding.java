package efub.gift_u.funding.domain;

import efub.gift_u.gift.domain.Gift;
import efub.gift_u.global.entity.BaseTimeEntity;
import efub.gift_u.participation.domain.Participation;
import efub.gift_u.review.domain.Review;
import efub.gift_u.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long fundingId;

    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, nullable = false)
    private User user;

    @Column(nullable = false)
    private String fundingTitle;

    @Column(nullable = false)
    private String fundingContent;

    @Column(nullable = false)
    private Date fundingStartDate;

    @Column(nullable = false)
    private Date fundingEndDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    private Boolean visibility;

    @Column
    private Long password;

    @Column
    private Long nowMoney;

    @Column(nullable = false)
    private String fundingImageUrl;

    @OneToMany(mappedBy = "reviewId", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "participationId", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Participation> participationList = new ArrayList<>();

    @OneToMany(mappedBy = "giftId", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Gift> giftList = new ArrayList<>();

    @Builder
    public Funding(User user, String fundingTitle, String fundingContent, Date fundingStartDate, Date fundingEndDate, String status, String deliveryAddress, Boolean visibility, Long password, Long nowMoney, String fundingImageUrl) {
        this.user = user;
        this.fundingTitle = fundingTitle;
        this.fundingContent = fundingContent;
        this.fundingStartDate = fundingStartDate;
        this.fundingEndDate = fundingEndDate;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.visibility = visibility;
        this.password = password;
        this.nowMoney = nowMoney;
        this.fundingImageUrl = fundingImageUrl;
    }
}
