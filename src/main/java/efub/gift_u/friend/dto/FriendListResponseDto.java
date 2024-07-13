package efub.gift_u.friend.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendListResponseDto {
    private List<FriendDetailDto> friends;
    private int friendCount;

    public FriendListResponseDto(List<FriendDetailDto> friends, int friendCount) {
        this.friends = friends;
        this.friendCount = friendCount;
    }
}




