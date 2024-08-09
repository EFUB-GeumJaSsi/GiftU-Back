package efub.gift_u.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import efub.gift_u.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String nickname;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    private String userImageUrl;
    private int friendCount;

    public static UserResponseDto from (User user, int friendCount) {
        return new UserResponseDto(user.getUserId(),
                user.getNickname(),
                user.getEmail(),
                user.getBirthday(),
                user.getUserImageUrl(),
                friendCount);
    }
}
