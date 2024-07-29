package efub.gift_u.domain.friend.controller;

import efub.gift_u.domain.friend.dto.FriendListResponseDto;
import efub.gift_u.domain.friend.dto.FriendRequestDto;
import efub.gift_u.domain.friend.dto.FriendResponseDto;
import efub.gift_u.domain.friend.service.FriendService;
import efub.gift_u.domain.oauth.customAnnotation.AuthUser;
import efub.gift_u.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    //친구 요청
    @PostMapping("/request")
    public FriendResponseDto requestFriend(@AuthUser User user, @RequestBody FriendRequestDto requestDto) {
        return friendService.requestFriend(user, requestDto);
    }

    //친구 요청 수락
    @PostMapping("/accept")
    public FriendResponseDto acceptFriendRequest(@AuthUser User user, @RequestParam Long friendTableId) {
        return friendService.acceptFriend(user, friendTableId);
    }

    //친구 요청 거절
    @PostMapping("/reject")
    public FriendResponseDto rejectFriendRequest(@AuthUser User user, @RequestParam Long friendTableId) {
        return friendService.rejectFriend(user, friendTableId);
    }

    //친구 목록 조회
    @GetMapping
    public FriendListResponseDto getFriends(@AuthUser User user) {
        return friendService.getFriends(user);
    }

    //친구 삭제
    @DeleteMapping("/{friendId}")
    public ResponseEntity<String> deleteFriend(@AuthUser User currentUser, @PathVariable Long friendId) {
        String message = friendService.deleteFriend(currentUser, friendId);
        return ResponseEntity.ok(message);
    }
}

