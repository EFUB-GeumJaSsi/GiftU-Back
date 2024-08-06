package efub.gift_u.domain.friend.service;

import efub.gift_u.domain.friend.domain.Friend;
import efub.gift_u.domain.friend.domain.FriendStatus;
import efub.gift_u.domain.friend.dto.*;
import efub.gift_u.domain.friend.repository.FriendRepository;
import efub.gift_u.domain.user.domain.User;
import efub.gift_u.domain.user.service.UserService;
import efub.gift_u.domain.user.repository.UserRepository;
import efub.gift_u.global.exception.CustomException;
import efub.gift_u.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public FriendResponseDto requestFriend(User currentUser, FriendRequestDto requestDto) {
        User friend = userService.findUserByEmail(requestDto.getEmail());
        if (currentUser.getUserId().equals(friend.getUserId())) {
            throw new CustomException(ErrorCode.SELF_FRIEND_REQUEST_NOT_ALLOWED);
        }

        User firstUser = currentUser.getUserId() < friend.getUserId() ? currentUser : friend;
        User secondUser = currentUser.getUserId() > friend.getUserId() ? currentUser : friend;

        FriendStatus status = (firstUser.equals(currentUser)) ? FriendStatus.PENDING_FIRST_SECOND : FriendStatus.PENDING_SECOND_FIRST;

        Friend friendRequest = Friend.builder()
                .firstUser(firstUser)
                .secondUser(secondUser)
                .status(status)
                .build();

        Friend savedFriend = friendRepository.save(friendRequest);

        return FriendResponseDto.from(savedFriend, currentUser);
    }

    public FriendResponseDto acceptFriend(User currentUser, Long friendTableId) {
        Friend friendRequest = friendRepository.findById(friendTableId)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

        if (friendRequest.getStatus() == FriendStatus.REJECTED) {
            throw new CustomException(ErrorCode.ACCEPT_AFTER_REJECT);
        }

        if ((friendRequest.getStatus() == FriendStatus.PENDING_FIRST_SECOND && friendRequest.getSecondUser().equals(currentUser)) ||
                (friendRequest.getStatus() == FriendStatus.PENDING_SECOND_FIRST && friendRequest.getFirstUser().equals(currentUser))) {
            friendRequest.accept();
        } else {
            throw new CustomException(ErrorCode.FAIL_AUTHORIZATION);
        }

        Friend savedFriend = friendRepository.save(friendRequest);

        return FriendResponseDto.from(savedFriend, currentUser);
    }

    public FriendResponseDto rejectFriend(User currentUser, Long friendTableId) {
        Friend friendRequest = friendRepository.findById(friendTableId)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

        if (friendRequest.getStatus() == FriendStatus.ACCEPTED) {
            throw new CustomException(ErrorCode.REJECT_AFTER_ACCEPT);
        }

        if ((friendRequest.getStatus() == FriendStatus.PENDING_FIRST_SECOND && friendRequest.getSecondUser().equals(currentUser)) ||
                (friendRequest.getStatus() == FriendStatus.PENDING_SECOND_FIRST && friendRequest.getFirstUser().equals(currentUser))) {
            friendRequest.reject();
        } else {
            throw new CustomException(ErrorCode.FAIL_AUTHORIZATION);
        }

        Friend savedFriend = friendRepository.save(friendRequest);

        return FriendResponseDto.from(savedFriend, currentUser);
    }

    public FriendListResponseDto getFriends(User currentUser) {
        List<Friend> friendsAsFirstUser = friendRepository.findAllByFirstUserAndStatus(currentUser, FriendStatus.ACCEPTED);
        List<Friend> friendsAsSecondUser = friendRepository.findAllBySecondUserAndStatus(currentUser, FriendStatus.ACCEPTED);

        List<FriendDetailDto> friendsDto = friendsAsFirstUser.stream()
                .map(friend -> FriendDetailDto.from(friend, currentUser))
                .collect(Collectors.toList());

        friendsDto.addAll(friendsAsSecondUser.stream()
                .map(friend -> FriendDetailDto.from(friend, currentUser))
                .toList());

        int friendCount = friendsDto.size();

        return new FriendListResponseDto(friendsDto, friendCount);
    }

    // 최근 펀딩에 참여한 친구 목록 조회
    public List<FriendParticipationListDto> getRecentFundingParticipationFriends(User user) {
        return friendRepository.findFundingParticipationFriends(user.getUserId()).stream()
                .map(FriendParticipationListDto::from)
                .collect(Collectors.toList());
    }

    public String deleteFriend(User currentUser, Long friendId) {
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        User firstUser = currentUser.getUserId() < friend.getUserId() ? currentUser : friend;
        User secondUser = currentUser.getUserId() > friend.getUserId() ? currentUser : friend;

        Friend friendRequest = friendRepository.findByFirstUserAndSecondUser(firstUser, secondUser)
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

        if (friendRequest.getStatus() != FriendStatus.ACCEPTED) {
            throw new CustomException(ErrorCode.NOT_FRIEND);
        }

        friendRepository.delete(friendRequest);

        return "친구가 삭제되었습니다.";
    }
}
