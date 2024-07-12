package efub.gift_u.friend.service;

import com.sun.jdi.request.DuplicateRequestException;
import efub.gift_u.friend.domain.Friend;
import efub.gift_u.friend.domain.FriendStatus;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendResponseDto requestFriend(User user, FriendRequestDto requestDto) {
        Optional<User> friendOption = userRepository.findByEmailOrNickname(requestDto.getEmail(), requestDto.getNickname());
        User friend = friendOption.orElseThrow(()-> new EntityNotFoundException("회원이 존재하지 않습니다."));

        if (friendRepository.findByUserAndFriend(user, friend).isPresent()) {
            throw new DuplicateRequestException("이미 친구 요청을 보냈습니다.");
        }

        //user: 친구 요청한 사람, friend: 친구 요청 받는 사람
        Friend friendRequest = Friend.builder()
                .user(user)
                .friend(friend)
                .status(FriendStatus.WAITING)
                .build();

        //양방향 친구 요청 관계 생성(user: 친구 요청 받는 사람, friend: 친구 요청한 사람)
        Friend reverseFriendRequest = Friend.builder()
                .user(friend)
                .friend(user)
                .status(FriendStatus.WAITING)
                .build();

        friendRepository.save(reverseFriendRequest);
        Friend savedFriend = friendRepository.save(friendRequest);

        return FriendResponseDto.from(savedFriend);
    }

    public FriendResponseDto acceptFriend(Long friendTableId) {
        Friend friendRequest = friendRepository.findById(friendTableId)
                .orElseThrow(()-> new EntityNotFoundException("친구 요청을 찾을 수 없습니다.")); //친구 요청이 존재해야 수락 가능함

        if (friendRequest.getStatus() == FriendStatus.ACCEPTED){
            throw new IllegalStateException("이미 수락된 요청입니다."); //이미 친구 요청을 수락한 경우, 다시 수락할 수 없음
        }

        if (friendRequest.getStatus() == FriendStatus.REJECTED){
            throw new IllegalStateException("이미 거절된 요청입니다."); //이미 친구 요청을 수락한 경우, 이후 거절할 수 없음
        }

        friendRequest.accept();

        //반대의 경우도 수락으로 업데이트
        Friend reverseFriendAccept = friendRepository.findByUserAndFriend(friendRequest.getFriend(), friendRequest.getUser())
                .orElseThrow(()-> new EntityNotFoundException("친구 요청을 찾을 수 없습니다.")); //친구 요청이 존재해야 수락 가능함
        reverseFriendAccept.accept();

        friendRepository.save(reverseFriendAccept);
        Friend savedFriend = friendRepository.save(friendRequest);

        return FriendResponseDto.from(savedFriend);
    }

    public FriendResponseDto rejectFriend(Long friendTableId) {
        Friend friendRequest = friendRepository.findById(friendTableId)
                .orElseThrow(() -> new EntityNotFoundException("친구 요청을 찾을 수 없습니다.")); //친구 요청이 존재해야 거절 가능함

        if (friendRequest.getStatus() == FriendStatus.REJECTED){
            throw new IllegalStateException("이미 거절된 요청입니다."); //이미 친구 요청을 거절한 경우, 다시 거절할 수 없음
        }
        if (friendRequest.getStatus() == FriendStatus.ACCEPTED){
            throw new IllegalStateException("이미 수락된 요청입니다."); //이미 친구 요청을 거절한 경우, 이후 수락할 수 없음
        }

        friendRequest.reject();

        // 반대의 경우도 거절로 업데이트
        Friend reverseFriendRequest = friendRepository.findByUserAndFriend(friendRequest.getFriend(), friendRequest.getUser())
                .orElseThrow(() -> new EntityNotFoundException("친구 요청을 찾을 수 없습니다.")); //친구 요청이 존재해야 거절 가능함
        reverseFriendRequest.reject();

        friendRepository.save(reverseFriendRequest);
        Friend savedFriend = friendRepository.save(friendRequest);

        return FriendResponseDto.from(savedFriend);
    }

    public FriendListResponseDto getFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        List<Friend> friendsAsUser = friendRepository.findAllByUserAndStatus(user, FriendStatus.ACCEPTED);

        List<FriendResponseDto> friendsDto = friendsAsUser.stream()
                .map(FriendResponseDto::from)
                .collect(Collectors.toList());

        int friendCount = friendsDto.size();

        return new FriendListResponseDto(friendsDto, friendCount);
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        Friend friendRequest = friendRepository.findByUserAndFriend(user, friend)
                .orElseThrow(() -> new EntityNotFoundException("친구 요청을 찾을 수 없습니다."));

        if (friendRequest.getStatus() != FriendStatus.ACCEPTED) {
            throw new IllegalStateException("현재 친구가 아닙니다. 친구를 삭제할 수 없습니다.");
        }

        // 양방향 친구 관계 삭제
        friendRepository.deleteByUserAndFriend(user, friend);
        friendRepository.deleteByUserAndFriend(friend, user);


    }

}

