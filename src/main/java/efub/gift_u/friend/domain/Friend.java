package efub.gift_u.friend.domain;

import efub.gift_u.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {
    @Id
    @Column(name = "friendId", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne
    @JoinColumn(name = "fromUserId", updatable = false, nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "toUserId", updatable = false, nullable = false)
    private User toUser;

    @Column(nullable = false)
    private Boolean isConfirmed;

    @Builder
    public Friend(User fromUser, User toUser, Boolean isConfirmed) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.isConfirmed = isConfirmed;
    }
}
