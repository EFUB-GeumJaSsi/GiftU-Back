package efub.gift_u.domain.friend.repository;

import efub.gift_u.domain.friend.domain.Friend;
import efub.gift_u.domain.friend.domain.FriendStatus;
import efub.gift_u.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByFirstUserAndSecondUser(User firstUser, User secondUser);
    List<Friend> findAllByFirstUserAndStatus(User firstUser, FriendStatus status);
    List<Friend> findAllBySecondUserAndStatus(User secondUser, FriendStatus status);

    @Query("SELECT p.user FROM Participation p JOIN Friend f ON p.user.userId = f.secondUser.userId WHERE f.firstUser.userId = :userId AND f.status = 'ACCEPTED' ORDER BY p.createdAt DESC")
    List<User> findFundingParticipationFriends(@Param("userId") Long userId);
}