package efub.gift_u.domain.funding.domain;

import efub.gift_u.domain.delivery.domain.Delivery;
import efub.gift_u.domain.gift.domain.Gift;
import efub.gift_u.global.entity.BaseTimeEntity;
import efub.gift_u.domain.participation.domain.Participation;
import efub.gift_u.domain.review.domain.Review;
import efub.gift_u.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDate fundingStartDate;

    @Column(nullable = false)
    private LocalDate fundingEndDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FundingStatus status;

    @Embedded
    private Delivery delivery;

    @Column(nullable = false)
    private Boolean visibility;

    @Column
    private String password;

    @Column
    private Long nowMoney;

    @Column(nullable = false)
    private String fundingImageUrl;

    @OneToMany(mappedBy = "funding", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "funding", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Participation> participationList = new ArrayList<>();

    @OneToMany(mappedBy = "funding", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Gift> giftList = new ArrayList<>();

    @Transient
    private LocalDateTime updatedAt;

    @Builder
    public Funding(User user, String fundingTitle, String fundingContent, LocalDate fundingStartDate, LocalDate fundingEndDate, FundingStatus status, Delivery delivery, Boolean visibility, String password, Long nowMoney, String fundingImageUrl) {
        this.user = user;
        this.fundingTitle = fundingTitle;
        this.fundingContent = fundingContent;
        this.fundingStartDate = fundingStartDate;
        this.fundingEndDate = fundingEndDate;
        this.status = status;
        this.delivery = delivery;
        this.visibility = visibility;
        this.password = password;
        this.nowMoney = nowMoney;
        this.fundingImageUrl = fundingImageUrl;
        this.giftList = new ArrayList<>();
    }

    //펀딩 현재 모인 금액으로 업데이트
    public void updateNowMoney(Long toAddAmount){
       if(toAddAmount != null){
           this.nowMoney = this.nowMoney + toAddAmount;
       }
    }

    //펀딩 상태 변경
    public void terminateIfExpired(){
        if (this.fundingEndDate.isBefore(LocalDate.now())){
            this.status = FundingStatus.TERMINATED;
        }
    }
}
