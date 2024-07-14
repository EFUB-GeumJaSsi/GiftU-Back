package efub.gift_u.participation.domain;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.global.entity.BaseTimeEntity;
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
public class Participation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long participationId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "fundingId", nullable = false)
    private Funding funding;

    @Column(nullable = false)
    private Long contributionAmount;

    @Column(nullable = false)
    private Boolean anonymous;

    // 펀딩참여시간은 공통적으로 createdAt 상속받아 사용하도록 함

    @Column
    private String message;

    @Builder
    public Participation(User user, Funding funding, Long contributionAmount, Boolean anonymous , String message) {
        this.user = user;
        this.funding = funding;
        this.contributionAmount = contributionAmount;
        this.anonymous = anonymous;
        this.message = message;
    }
}
