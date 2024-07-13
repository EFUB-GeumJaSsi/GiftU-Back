package efub.gift_u.oauth.service;

import efub.gift_u.oauth.jwt.JwtService;
import efub.gift_u.user.domain.User;
import efub.gift_u.oauth.jwt.JwtTokens;
import efub.gift_u.oauth.dto.KakaoInfoResponseDto;
import efub.gift_u.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final KakaoApiClient kakaoApiClient;
    private final JwtService jwtService;

    public JwtTokens login(String code) {
        // 인가 코드로 액세스 코드 받아옴
        String kakaoAccessToken = kakaoApiClient.getAccessToken(code);
        // 액세스 토큰으로 사용자 정보 받아옴
        KakaoInfoResponseDto kakaoInfoResponseDto = kakaoApiClient.getUserInfo(kakaoAccessToken);

        // 받아온 정보 중 이메일로 유저 찾기 (없으면 생성 - 유저 등록)
        Long userId = findOrCreateUser(kakaoInfoResponseDto);
        // 액세스 토큰 저장
        saveKakaoAccessToken(userId, kakaoAccessToken);

        // 해당 유저의 jwt 인증 토큰 생성해 반환
        return jwtService.generateJwtToken(userId);
    }


    // 이메일로 유저 찾기 메서드 //
    private Long findOrCreateUser(KakaoInfoResponseDto kakaoInfoResponseDto) {
        return userRepository.findByEmail(kakaoInfoResponseDto.getEmail())
                .map(User::getUserId)
                .orElseGet(() -> newUser(kakaoInfoResponseDto));
    }

    // 유저 생성 메서드 //
    private Long newUser(KakaoInfoResponseDto kakaoInfoResponseDto) {
        User user = User.builder()
                .email(kakaoInfoResponseDto.getEmail())
                .nickname(kakaoInfoResponseDto.getEmail())
                .build();
        return userRepository.save(user).getUserId();
    }

    // 카카오 액세스 토큰 저장 //
    private void saveKakaoAccessToken(Long userId, String kakaoAccessToken){
        User user = userRepository.findByUserId(userId);
        user.updateKakaoAccessToken(kakaoAccessToken);
        userRepository.save(user);
    }

}
