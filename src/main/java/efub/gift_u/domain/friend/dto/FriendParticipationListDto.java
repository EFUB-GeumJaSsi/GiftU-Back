package efub.gift_u.domain.friend.dto;

import efub.gift_u.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendParticipationListDto {
    private Long userId;
    private String nickname;
    private Date birthday;
    private String userImageUrl;

    public FriendParticipationListDto(Long userId, String nickname, Date birthday, String userImageUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.birthday = birthday;
        this.userImageUrl = userImageUrl;
    }

    public static FriendParticipationListDto from(User user) {
        return new FriendParticipationListDto(
                user.getUserId(),
                user.getNickname(),
                user.getBirthday(),
                user.getUserImageUrl()
        );
    }

}
