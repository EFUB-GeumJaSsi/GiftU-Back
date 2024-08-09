package efub.gift_u.domain.friend.dto;

import efub.gift_u.domain.friend.domain.Friend;
import efub.gift_u.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendDetailDto {
    private Long friendTableId;
    private Long friendId;
    private String nickname;
    private String email;
    private String birthday;
    private String userImageUrl;

    public FriendDetailDto(Long friendTableId, Long friendId, String nickname, String email, String birthday, String userImageUrl) {
        this.friendTableId = friendTableId;
        this.friendId = friendId;
        this.nickname = nickname;
        this.email = email;
        this.birthday = birthday;
        this.userImageUrl = userImageUrl;
    }

    public static FriendDetailDto from(Friend friend, User currentUser) {
        User friendUser = friend.getFirstUser().equals(currentUser) ? friend.getSecondUser() : friend.getFirstUser();
        String birthday = (friendUser.getBirthday() != null) ? friendUser.getBirthday().toString() : null;
        return new FriendDetailDto(
                friend.getFriendTableId(),
                friendUser.getUserId(),
                friendUser.getNickname(),
                friendUser.getEmail(),
                birthday,
                friendUser.getUserImageUrl()
        );
    }
}

