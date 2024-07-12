package efub.gift_u.notice.domain;

import efub.gift_u.global.entity.BaseTimeEntity;
import efub.gift_u.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeId", updatable = false)
    private Long noticeId;

    @ManyToOne
    @JoinColumn(name ="UserId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String message;


}
