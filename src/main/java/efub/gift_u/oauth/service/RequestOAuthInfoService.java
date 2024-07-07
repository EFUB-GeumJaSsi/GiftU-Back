package efub.gift_u.oauth.service;

import efub.gift_u.oauth.dto.KakaoInfoResponse;
import efub.gift_u.oauth.dto.KakaoInfoWithTokenDto;
import org.springframework.stereotype.Component;

@Component
public class RequestOAuthInfoService {
    private final KakaoApiClient clients;

    public RequestOAuthInfoService(KakaoApiClient clients) {
        this.clients = clients;
    }

    public KakaoInfoWithTokenDto request(String code) {
        String accessToken = clients.getAccessToken(code);
        return clients.getUserInfo(accessToken);
    }

}
