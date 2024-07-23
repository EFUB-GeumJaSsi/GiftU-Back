package efub.gift_u.domain.notice.controller;


import efub.gift_u.domain.notice.service.NoticeService;
import efub.gift_u.domain.oauth.customAnnotation.AuthUser;
import efub.gift_u.domain.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /*친구 알림 조회*/
    @GetMapping("/friend")
    public ResponseEntity<?> getFriendNotice(@AuthUser User user){
       return noticeService.getFriendNotice(user);
    }

    /*펀딩 알림 조회 */
    @GetMapping("/funding")
    public ResponseEntity<?> getFundingNotice(@AuthUser User user){
        return noticeService.getFundingNotice(user);
    }

    /* 모든 알림 조회 */
    @GetMapping
    public ResponseEntity<?> getAllNotice(@AuthUser User user){
        return noticeService.getAllNotice(user);
    }

}
