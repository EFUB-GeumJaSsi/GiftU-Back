package efub.gift_u.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import efub.gift_u.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserUpdateResponseDto {
    private Long userId;
    private String nickname;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    private String userImageUrl;

    public static UserUpdateResponseDto from (User user) {
        return new UserUpdateResponseDto(user.getUserId(),
                user.getNickname(),
                user.getEmail(),
                user.getBirthday(),
                user.getUserImageUrl());
    }
}

