package efub.gift_u.domain.user.service;

import efub.gift_u.domain.user.repository.UserRepository;
import efub.gift_u.global.S3Image.service.S3ImageService;
import efub.gift_u.domain.user.domain.User;
import efub.gift_u.domain.user.dto.UserUpdateRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 email를 가진 User를 찾을 수 없습니다. email="+email));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 User를 찾을 수 없습니다.id="+id));
    }

    @Transactional
    public User updateUser(User user, UserUpdateRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        if (!user.getNickname().equals(requestDto.getNickname()) &&
                userRepository.findByNickname(requestDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        String userImageUrl = requestDto.getUserImageUrl();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            // 새로운 이미지를 업로드
            String newFileName = s3ImageService.upload(multipartFile, "images/userImages");
            String newFileUrl = s3ImageService.getFileUrl(newFileName);

            // 기존 이미지를 삭제
            if (user.getUserImageUrl() != null && !user.getUserImageUrl().isEmpty()) {
                String oldFileName = user.getUserImageUrl().substring(user.getUserImageUrl().indexOf("images/userImages"));
                s3ImageService.delete(oldFileName);
            }

            //새로운 이미지 URL로 업데이트
            userImageUrl = newFileUrl;
        }

        user.updateUser(requestDto.getNickname(), requestDto.getBirthday(), userImageUrl);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(User user){

        // S3에서 이미지 삭제
        if (user.getUserImageUrl() != null && !user.getUserImageUrl().isEmpty()) {
            String fileName = user.getUserImageUrl().substring(user.getUserImageUrl().indexOf("images/userImages"));
            s3ImageService.delete(fileName);
        }

        userRepository.delete(user);
    }

}



