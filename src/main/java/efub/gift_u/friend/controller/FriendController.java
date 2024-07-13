package efub.gift_u.friend.controller;

import efub.gift_u.friend.dto.FriendRequestDto;
import efub.gift_u.friend.dto.FriendResponseDto;
import efub.gift_u.friend.dto.FriendListResponseDto;
import efub.gift_u.friend.service.FriendService;
import efub.gift_u.user.domain.User;
import efub.gift_u.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("/request")
    public FriendResponseDto requestFriend(@RequestParam Long userId, @RequestBody FriendRequestDto requestDto) {
        User currentUser = userService.findUserById(userId);
        return friendService.requestFriend(currentUser, requestDto);
    }

    @PostMapping("/accept")
    public FriendResponseDto acceptFriendRequest(@RequestParam Long userId, @RequestParam Long friendTableId) {
        User currentUser = userService.findUserById(userId);
        return friendService.acceptFriend(currentUser, friendTableId);
    }

    @PostMapping("/reject")
    public FriendResponseDto rejectFriendRequest(@RequestParam Long userId, @RequestParam Long friendTableId) {
        User currentUser = userService.findUserById(userId);
        return friendService.rejectFriend(currentUser, friendTableId);
    }

    @GetMapping
    public FriendListResponseDto getFriends(@RequestParam Long userId) {
        User currentUser = userService.findUserById(userId);
        return friendService.getFriends(currentUser);
    }

    @DeleteMapping("/{userId}/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        User currentUser = userService.findUserById(userId);
        String message = friendService.deleteFriend(currentUser, friendId);
        return ResponseEntity.ok(message);
    }
}

