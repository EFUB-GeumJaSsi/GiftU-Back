package efub.gift_u.friend.controller;

import efub.gift_u.friend.dto.FriendListResponseDto;
import efub.gift_u.friend.dto.FriendRequestDto;
import efub.gift_u.friend.dto.FriendResponseDto;
import efub.gift_u.friend.service.FriendService;
import efub.gift_u.oauth.customAnnotation.AuthUser;
import efub.gift_u.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("/request")
    public FriendResponseDto requestFriend(@AuthUser User user, @RequestBody FriendRequestDto requestDto) {
        return friendService.requestFriend(user, requestDto);
    }

    @PostMapping("/accept")
    public FriendResponseDto acceptFriendRequest(@AuthUser User user, @RequestParam Long friendTableId) {
        return friendService.acceptFriend(user, friendTableId);
    }

    @PostMapping("/reject")
    public FriendResponseDto rejectFriendRequest(@AuthUser User user, @RequestParam Long friendTableId) {
        return friendService.rejectFriend(user, friendTableId);
    }

    @GetMapping
    public FriendListResponseDto getFriends(@AuthUser User user) {
        return friendService.getFriends(user);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<String> deleteFriend(@AuthUser User currentUser, @PathVariable Long friendId) {
        String message = friendService.deleteFriend(currentUser, friendId);
        return ResponseEntity.ok(message);
    }
}

