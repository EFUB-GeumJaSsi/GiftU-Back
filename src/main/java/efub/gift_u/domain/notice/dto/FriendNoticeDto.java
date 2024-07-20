package efub.gift_u.domain.notice.dto;

import efub.gift_u.domain.friend.domain.Friend;
import efub.gift_u.domain.friend.domain.FriendStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendNoticeDto {

    private Long friendTableId;
    private Long firstUserId;
    private Long secondUserId;
    private FriendStatus status;
    private LocalDateTime updatedAt;

    public static FriendNoticeDto from(Friend friend) {
        return new FriendNoticeDto(
               friend.getFriendTableId(),
                friend.getFirstUser().getUserId(),
                friend.getSecondUser().getUserId(),
                friend.getStatus(),
                friend.getUpdatedAt()
        );
    }

}
