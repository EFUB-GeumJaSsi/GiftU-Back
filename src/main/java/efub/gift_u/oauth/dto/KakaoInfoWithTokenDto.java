package efub.gift_u.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoInfoWithTokenDto {
    private KakaoInfoResponse kakaoInfoResponse;
    private String accessToken;
}
