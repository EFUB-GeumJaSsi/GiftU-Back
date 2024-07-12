package efub.gift_u.friend.dto;

import efub.gift_u.friend.domain.Friend;
import efub.gift_u.friend.domain.FriendStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendResponseDto {
    private Long friendTableId;
    private Long userId;
    private Long friendId;
    private String friendNickname;
    private String friendEmail;
    private FriendStatus status;

    @Builder
    public FriendResponseDto(Long friendTableId, Long userId, Long friendId, String friendNickname, String friendEmail, FriendStatus status) {
        this.friendTableId = friendTableId;
        this.userId = userId;
        this.friendId = friendId;
        this.friendNickname = friendNickname;
        this.friendEmail = friendEmail;
        this.status = status;
    }

    public static FriendResponseDto from(Friend friend) {
        return FriendResponseDto.builder()
                .friendTableId(friend.getFriendTableId())
                .userId(friend.getUser().getUserId())
                .friendId(friend.getFriend().getUserId())
                .friendNickname(friend.getFriend().getNickname())
                .friendEmail(friend.getFriend().getEmail())
                .status(friend.getStatus())
                .build();
    }
}

