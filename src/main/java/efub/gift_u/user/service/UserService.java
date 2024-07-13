package efub.gift_u.user.service;

import efub.gift_u.user.domain.User;
import efub.gift_u.user.dto.UserUpdateRequestDto;
import efub.gift_u.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 email를 가진 User를 찾을 수 없습니다. email="+email));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 User를 찾을 수 없습니다.id="+id));
    }

    @Transactional
    public User updateUser(User user, UserUpdateRequestDto requestDto){
        user.updateUser(requestDto.getNickname(), requestDto.getBirthday(), requestDto.getUserImageUrl());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(User user){
        userRepository.delete(user);
    }

}
