package efub.gift_u.friend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendRequestDto {
    private String nickname;
    private String email;

    @Builder
    public FriendRequestDto(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
