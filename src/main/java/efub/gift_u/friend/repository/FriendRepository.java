package efub.gift_u.friend.repository;

import efub.gift_u.friend.domain.Friend;
import efub.gift_u.friend.domain.FriendStatus;
import efub.gift_u.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByFirstUserAndSecondUser(User firstUser, User secondUser);
    List<Friend> findAllByFirstUserAndStatus(User firstUser, FriendStatus status);
    List<Friend> findAllBySecondUserAndStatus(User secondUser, FriendStatus status);
}
