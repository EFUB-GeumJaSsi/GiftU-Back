package efub.gift_u.domain.friend.dto;

import efub.gift_u.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class FriendParticipationListDto {
    private Long userId;
    private String nickname;
    private Date birthday;
    private String userImageUrl;

    public static FriendParticipationListDto from(User user) {
        return new FriendParticipationListDto(
                user.getUserId(),
                user.getNickname(),
                user.getBirthday(),
                user.getUserImageUrl()
        );
    }

}
