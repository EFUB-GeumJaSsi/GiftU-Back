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
    @Column(name = "friendTableId", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendTableId;

    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "friendId", updatable = false, nullable = false)
    private User friend;

    @Column(nullable = false)
    private Boolean isConfirmed;

    @Builder
    public Friend(User user, User friend, Boolean isConfirmed) {
        this.user = user;
        this.friend = friend;
        this.isConfirmed = isConfirmed;
    }
}
