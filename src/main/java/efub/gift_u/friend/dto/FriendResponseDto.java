package efub.gift_u.friend.dto;

import efub.gift_u.friend.domain.Friend;
import efub.gift_u.friend.domain.FriendStatus;
import efub.gift_u.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendResponseDto {
    private Long friendTableId;
    private Long firstUserId;
    private Long secondUserId;
    private String friendNickname;
    private String friendEmail;
    private FriendStatus status;

    public FriendResponseDto(Long friendTableId, Long firstUserId, Long secondUserId, String friendNickname, String friendEmail, FriendStatus status) {
        this.friendTableId = friendTableId;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.friendNickname = friendNickname;
        this.friendEmail = friendEmail;
        this.status = status;
    }

    public static FriendResponseDto from(Friend friend, User user) {
        User friendUser = friend.getFirstUser().equals(user) ? friend.getSecondUser() : friend.getFirstUser();
        return new FriendResponseDto(
                friend.getFriendTableId(),
                friend.getFirstUser().getUserId(),
                friend.getSecondUser().getUserId(),
                friendUser.getNickname(),
                friendUser.getEmail(),
                friend.getStatus()
        );
    }
}


