package efub.gift_u.friend.service;

import com.sun.jdi.request.DuplicateRequestException;
import efub.gift_u.friend.domain.Friend;
import efub.gift_u.friend.domain.FriendStatus;
import efub.gift_u.friend.dto.FriendDetailDto;
import efub.gift_u.friend.dto.FriendListResponseDto;
import efub.gift_u.friend.dto.FriendRequestDto;
import efub.gift_u.friend.dto.FriendResponseDto;
import efub.gift_u.friend.repository.FriendRepository;
import efub.gift_u.user.domain.User;
import efub.gift_u.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public FriendResponseDto requestFriend(User currentUser, FriendRequestDto requestDto) {
        User friend = userRepository.findByEmailOrNickname(requestDto.getEmail(), requestDto.getNickname())
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        if (currentUser.getUserId().equals(friend.getUserId())) {
            throw new IllegalArgumentException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        User firstUser = currentUser.getUserId() < friend.getUserId() ? currentUser : friend;
        User secondUser = currentUser.getUserId() > friend.getUserId() ? currentUser : friend;

        FriendStatus status = (firstUser.equals(currentUser)) ? FriendStatus.PENDING_FIRST_SECOND : FriendStatus.PENDING_SECOND_FIRST;

        if (friendRepository.findByFirstUserAndSecondUser(firstUser, secondUser).isPresent()) {
            throw new DuplicateRequestException("친구 요청이 이미 존재하여 친구 요청을 보낼 수 없습니다.");
        }

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
                .orElseThrow(() -> new EntityNotFoundException("친구 요청을 찾을 수 없습니다."));

        if (friendRequest.getStatus() == FriendStatus.ACCEPTED) {
            throw new IllegalStateException("이미 수락된 요청입니다.");
        }

        if (friendRequest.getStatus() == FriendStatus.REJECTED) {
            throw new IllegalStateException("이미 거절된 요청입니다.");
        }

        if ((friendRequest.getStatus() == FriendStatus.PENDING_FIRST_SECOND && friendRequest.getSecondUser().equals(currentUser)) ||
                (friendRequest.getStatus() == FriendStatus.PENDING_SECOND_FIRST && friendRequest.getFirstUser().equals(currentUser))) {
            friendRequest.accept();
        } else {
            throw new IllegalStateException("권한이 없습니다.");
        }

        Friend savedFriend = friendRepository.save(friendRequest);

        return FriendResponseDto.from(savedFriend, currentUser);
    }

    public FriendResponseDto rejectFriend(User currentUser, Long friendTableId) {
        Friend friendRequest = friendRepository.findById(friendTableId)
                .orElseThrow(() -> new EntityNotFoundException("친구 요청을 찾을 수 없습니다."));

        if (friendRequest.getStatus() == FriendStatus.REJECTED) {
            throw new IllegalStateException("이미 거절된 요청입니다.");
        }

        if (friendRequest.getStatus() == FriendStatus.ACCEPTED) {
            throw new IllegalStateException("이미 수락된 요청입니다.");
        }

        if ((friendRequest.getStatus() == FriendStatus.PENDING_FIRST_SECOND && friendRequest.getSecondUser().equals(currentUser)) ||
                (friendRequest.getStatus() == FriendStatus.PENDING_SECOND_FIRST && friendRequest.getFirstUser().equals(currentUser))) {
            friendRequest.reject();
        } else {
            throw new IllegalStateException("권한이 없습니다.");
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

    public String deleteFriend(User currentUser, Long friendId) {
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        User firstUser = currentUser.getUserId() < friend.getUserId() ? currentUser : friend;
        User secondUser = currentUser.getUserId() > friend.getUserId() ? currentUser : friend;

        Friend friendRequest = friendRepository.findByFirstUserAndSecondUser(firstUser, secondUser)
                .orElseThrow(() -> new EntityNotFoundException("친구 요청을 찾을 수 없습니다."));

        if (friendRequest.getStatus() != FriendStatus.ACCEPTED) {
            throw new IllegalStateException("현재 친구가 아닙니다. 친구를 삭제할 수 없습니다.");
        }

        friendRepository.delete(friendRequest);

        return "친구가 삭제되었습니다.";
    }
}