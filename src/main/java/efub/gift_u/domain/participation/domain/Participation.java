package efub.gift_u.domain.participation.domain;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.global.entity.BaseTimeEntity;
import efub.gift_u.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Transient
    private LocalDateTime updatedAt;

    @Builder
    public Participation(User user, Funding funding, Long contributionAmount, Boolean anonymous , String message) {
        this.user = user;
        this.funding = funding;
        this.contributionAmount = contributionAmount;
        this.anonymous = anonymous;
        this.message = message;
    }

    // 펀딩 참여 익명성 여부 변경
   public void updateVisibility(Boolean newAnonymous){
         this.anonymous = newAnonymous;
   }

   // 펀딩 참여 메세지 변경
    public void updateMessage(String newMessage){
        this.message = newMessage;
    }
}
