package efub.gift_u.oauth.controller;

import efub.gift_u.oauth.service.OAuthLoginService;
import efub.gift_u.oauth.jwt.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    private final OAuthLoginService oAuthLoginService;

    // 인가 코드 파라미터로 받아서 ~ jwt 토큰 생성해 반환하는 메서드
    @ResponseBody
    @GetMapping("/kakao")
    public ResponseEntity<AuthTokens> kakaoCallback(@RequestParam(name="code") String code) {
        System.out.println(code);
        return ResponseEntity.ok(oAuthLoginService.login(code));
    }


}
