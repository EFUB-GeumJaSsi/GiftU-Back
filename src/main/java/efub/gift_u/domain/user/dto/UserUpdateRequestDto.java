package efub.gift_u.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String nickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    private String userImageUrl;

    @Builder
    public UserUpdateRequestDto(String nickname, Date birthday, String userImageUrl) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.userImageUrl = userImageUrl;
    }
}
