package efub.gift_u.user.controller;

import efub.gift_u.oauth.customAnnotation.AuthUser;
import efub.gift_u.oauth.service.KakaoApiClient;
import efub.gift_u.user.domain.User;
import efub.gift_u.user.dto.UserResponseDto;
import efub.gift_u.user.dto.UserUpdateRequestDto;
import efub.gift_u.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoApiClient kakaoApiClient;

    /* 회원 정보 조회 */
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto getUser(@AuthUser User user){
        return UserResponseDto.from(user);
    }

    /* 회원 정보 수정 */
    @PatchMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto updateUser(@AuthUser User user, @RequestBody @Valid final UserUpdateRequestDto requestDto) {
        User findedUser = userService.updateUser(user, requestDto);
        return UserResponseDto.from(findedUser);
    }


    /* 회원 탈퇴 */
    @DeleteMapping
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteUser(@AuthUser User user) {
        userService.deleteUser(user);
        String response = kakaoApiClient.UnlinkUser(user.getKakaoAccessToken());
        return "성공적으로 탈퇴되었습니다. 카카오계정ID : " + response;
    }

}

