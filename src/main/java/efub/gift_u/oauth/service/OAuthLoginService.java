package efub.gift_u.oauth.service;

import efub.gift_u.user.domain.User;
import efub.gift_u.oauth.jwt.AuthTokens;
import efub.gift_u.oauth.jwt.AuthTokensGenerator;
import efub.gift_u.oauth.dto.KakaoInfoResponse;
import efub.gift_u.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(String code) {
        // 인가 코드로 액세스 토큰오고 이 토큰으로 사용자 정보 받아옴
        KakaoInfoResponse kakaoInfoResponse = requestOAuthInfoService.request(code);
        // 받아온 정보 중 이메일로 유저 찾기 (없으면 생성)
        Long UserId = findOrCreateUser(kakaoInfoResponse);
        // 해당 유저의 jwt 인증 토큰 생성해 반환
        return authTokensGenerator.generate(UserId);
    }


    // 받아온 이메일로 유저 찾기 메서드 //
    private Long findOrCreateUser(KakaoInfoResponse kakaoInfoResponse) {
        return userRepository.findByEmail(kakaoInfoResponse.getEmail())
                .map(User::getUserId)
                .orElseGet(() -> newUser(kakaoInfoResponse));
    }

    // 유저 생성 메서드 //
    private Long newUser(KakaoInfoResponse kakaoInfoResponse) {
        User user = User.builder()
                .email(kakaoInfoResponse.getEmail())
                .build();

        return userRepository.save(user).getUserId();
    }
}
