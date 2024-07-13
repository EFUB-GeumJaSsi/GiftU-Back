package efub.gift_u.friend.dto;

import efub.gift_u.friend.domain.Friend;
import efub.gift_u.friend.domain.FriendStatus;
import efub.gift_u.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendResponseDto {
    private Long friendTableId;
    private Long firstUserId;
    private Long secondUserId;
    private String friendNickname;
    private String friendEmail;
    private FriendStatus status;

    @Builder
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
        return FriendResponseDto.builder()
                .friendTableId(friend.getFriendTableId())
                .firstUserId(friend.getFirstUser().getUserId())
                .secondUserId(friend.getSecondUser().getUserId())
                .friendNickname(friendUser.getNickname())
                .friendEmail(friendUser.getEmail())
                .status(friend.getStatus())
                .build();
    }
}


