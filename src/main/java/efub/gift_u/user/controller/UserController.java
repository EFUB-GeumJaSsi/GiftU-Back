package efub.gift_u.user.controller;

import efub.gift_u.oauth.customAnnotation.AuthUser;
import efub.gift_u.user.domain.User;
import efub.gift_u.user.dto.UserResponseDto;
import efub.gift_u.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 정보 조회
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto getUser(@AuthUser User user){
        Long userId = user.getUserId();
        return UserResponseDto.from(user);
    }
}

