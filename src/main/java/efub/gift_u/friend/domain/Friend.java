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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firstUserId", updatable = false, nullable = false)
    private User firstUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondUserId", updatable = false, nullable = false)
    private User secondUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendStatus status;

    @Builder
    public Friend(User firstUser, User secondUser, FriendStatus status) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.status = status;
    }

    public void accept() {
        this.status = FriendStatus.ACCEPTED;
    }

    public void reject() {
        this.status = FriendStatus.REJECTED;
    }

}
