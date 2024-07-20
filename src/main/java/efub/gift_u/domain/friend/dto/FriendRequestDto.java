package efub.gift_u.domain.friend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendRequestDto {
    private String email;

    @Builder
    public FriendRequestDto(String email) {
        this.email = email;
    }
}
