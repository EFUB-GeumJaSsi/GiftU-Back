package efub.gift_u.friend.controller;

import efub.gift_u.friend.dto.FriendListResponseDto;
import efub.gift_u.friend.dto.FriendRequestDto;
import efub.gift_u.friend.dto.FriendResponseDto;
import efub.gift_u.friend.service.FriendService;
import efub.gift_u.user.domain.User;
import efub.gift_u.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final UserRepository userRepository;

    @PostMapping("/request")
    public FriendResponseDto requestFriend(@RequestParam Long userId, @RequestBody FriendRequestDto requestDto) {
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        return friendService.requestFriend(currentUser, new FriendRequestDto(requestDto.getNickname(), requestDto.getEmail()));
    }

    @PostMapping("/accept")
    public FriendResponseDto acceptFriendRequest(@RequestParam Long friendTableId) {
        return friendService.acceptFriend(friendTableId);
    }

    @PostMapping("/reject")
    public FriendResponseDto rejectFriendRequest(@RequestParam Long friendTableId) {
        return friendService.rejectFriend(friendTableId);
    }

    @GetMapping("/{userId}")
    public FriendListResponseDto getFriends(@PathVariable Long userId) {
        return friendService.getFriends(userId);
    }

    @DeleteMapping("/{userId}/{friendId}")
    public String deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.deleteFriend(userId, friendId);

        return "친구가 삭제되었습니다.";
    }
}

