package efub.gift_u.domain.notice.dto;

import efub.gift_u.domain.friend.domain.Friend;
import efub.gift_u.domain.friend.domain.FriendStatus;
import efub.gift_u.domain.friend.dto.FriendDetailDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendNoticeDto {

    private Long friendTableId;
    private Long firstUserId;
    private Long secondUserId;
    private String recieveUserNickname;
    private String recieveUserImgUrl;
    private FriendStatus status;
    private LocalDateTime updatedAt;

    public FriendNoticeDto(Long friendTableId , Long firstUserId , Long secondUserId ,String recieveUserNickname , String recieveUserImgUrl ,FriendStatus status , LocalDateTime updatedAt){
        this.friendTableId = friendTableId;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.recieveUserNickname = recieveUserNickname;
        this.recieveUserImgUrl = recieveUserImgUrl;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public static FriendNoticeDto firstFrom(Friend friend) {
        return new FriendNoticeDto(
               friend.getFriendTableId(),
                friend.getFirstUser().getUserId(),
                friend.getSecondUser().getUserId(),
                friend.getSecondUser().getNickname(),
                friend.getSecondUser().getUserImageUrl(),
                friend.getStatus(),
                friend.getUpdatedAt()
        );
    }

    public static FriendNoticeDto secondFrom(Friend friend) {
        return new FriendNoticeDto(
                friend.getFriendTableId(),
                friend.getFirstUser().getUserId(),
                friend.getSecondUser().getUserId(),
                friend.getFirstUser().getNickname(),
                friend.getFirstUser().getUserImageUrl(),
                friend.getStatus(),
                friend.getUpdatedAt()
        );
    }


}
