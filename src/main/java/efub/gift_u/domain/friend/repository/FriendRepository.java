package efub.gift_u.domain.friend.repository;

import efub.gift_u.domain.friend.domain.Friend;
import efub.gift_u.domain.friend.domain.FriendStatus;
import efub.gift_u.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findAllByFirstUserAndSecondUser(User firstUser, User secondUser);
    List<Friend> findAllByFirstUserAndStatus(User firstUser, FriendStatus status);
    List<Friend> findAllBySecondUserAndStatus(User secondUser, FriendStatus status);
    List<Friend> findByFirstUserAndSecondUserAndStatusIn(User firstUser, User secondUser, List<FriendStatus> statuses);

    @Query("SELECT p.user FROM Participation p " + 
           "JOIN p.funding fnd " +
           "JOIN Friend f ON (p.user.userId = f.secondUser.userId OR p.user.userId = f.firstUser.userId) " +
           "WHERE (f.firstUser.userId = :userId OR f.secondUser.userId = :userId) AND fnd.userId = :userId AND f.status = 'ACCEPTED' " +
           "AND p.user.userId != :userId " + "GROUP BY p.user ORDER BY MAX(p.createdAt) DESC")
    List<User> findFundingParticipationFriends(@Param("userId") Long userId);


    @Query("SELECT f FROM Friend f JOIN FETCH f.firstUser WHERE f.firstUser = :firstUser AND f.status = :status")
    List<Friend> findAllByFirstUserAndStatusWithFetch(@Param("firstUser") User firstUser, @Param("status") FriendStatus status);

    @Query("SELECT f FROM Friend f JOIN FETCH f.secondUser WHERE f.secondUser = :secondUser AND f.status = :status")
    List<Friend> findAllBySecondUserAndStatusWithFetch(@Param("secondUser") User secondUser, @Param("status") FriendStatus status);

}
