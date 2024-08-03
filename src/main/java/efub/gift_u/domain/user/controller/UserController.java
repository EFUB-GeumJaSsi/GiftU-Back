package efub.gift_u.domain.user.controller;

import efub.gift_u.domain.friend.service.FriendService;
import efub.gift_u.domain.oauth.customAnnotation.AuthUser;
import efub.gift_u.domain.oauth.service.KakaoApiClient;
import efub.gift_u.domain.user.dto.UserResponseDto;
import efub.gift_u.domain.user.service.UserService;
import efub.gift_u.domain.user.domain.User;
import efub.gift_u.domain.user.dto.UserUpdateResponseDto;
import efub.gift_u.domain.user.dto.UserUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoApiClient kakaoApiClient;
    private final FriendService friendService;

    /* 회원 정보 조회 */
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto getUser(@AuthUser User user){
        int friendCount = friendService.getFriends(user).getFriendCount(); // 친구 수 가져오기
        return UserResponseDto.from(user, friendCount);
    }

    /* 회원 정보 수정 */
    @PatchMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserUpdateResponseDto updateUser(@AuthUser User user,
                                            @RequestPart(value = "userUpdateRequestDto", required = false) @Valid final UserUpdateRequestDto requestDto,
                                            @RequestPart(value = "userImage", required = false) MultipartFile multipartFile) throws IOException{
        User updatedUser = userService.updateUser(user, requestDto, multipartFile);
        return UserUpdateResponseDto.from(updatedUser);
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

