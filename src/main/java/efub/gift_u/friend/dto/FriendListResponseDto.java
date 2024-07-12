package efub.gift_u.friend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendListResponseDto {
    private List<FriendResponseDto> friends;
    private int friendCount;

    @Builder
    public FriendListResponseDto(List<FriendResponseDto> friends, int friendCount) {
        this.friends = friends;
        this.friendCount = friendCount;
    }
}

