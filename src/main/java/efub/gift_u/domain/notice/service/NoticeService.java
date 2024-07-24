package efub.gift_u.domain.notice.service;

import efub.gift_u.domain.friend.domain.Friend;
import efub.gift_u.domain.friend.repository.FriendRepository;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.repository.FundingRepository;
import efub.gift_u.domain.notice.dto.AllNoticeDto;
import efub.gift_u.domain.notice.dto.FriendNoticeDto;
import efub.gift_u.domain.notice.dto.FundingNoticeDto;
import efub.gift_u.domain.oauth.customAnnotation.AuthUser;
import efub.gift_u.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static efub.gift_u.domain.friend.domain.FriendStatus.PENDING_FIRST_SECOND;
import static efub.gift_u.domain.friend.domain.FriendStatus.PENDING_SECOND_FIRST;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final FriendRepository friendRepository;
    private final FundingRepository fundingRepository;

    LocalDate today = LocalDate.now(); //오늘 날짜
    

    /* 친구 알림 조회 , (현재 요청 상태인 것만) 함수  */
    private List<FriendNoticeDto> friendNotice(@AuthUser User user){

        // user_id가 first user인 경우 ,first user가 받는 알림은 first user에게 친구 신청을 한 경우,  pending_second_first
        List<Friend> noticeAllByFirstUser=  friendRepository.findAllByFirstUserAndStatus(user ,PENDING_SECOND_FIRST);
        List<FriendNoticeDto> firstNoticeDtos = noticeAllByFirstUser.stream()
                .map(notice -> FriendNoticeDto.from(notice))
                .collect(Collectors.toList());
        // user_id가 second user인 경우 , second user가 받는 알림은 second user에게 친구 신청을 한 경우 , pending_first_second
        List<Friend> noticeAllBySecondUser = friendRepository.findAllBySecondUserAndStatus(user , PENDING_FIRST_SECOND);
        List<FriendNoticeDto> secondNoticeDtos = noticeAllBySecondUser.stream()
                .map(notice -> FriendNoticeDto.from(notice))
                .collect(Collectors.toList());

        firstNoticeDtos.addAll(secondNoticeDtos);
        return firstNoticeDtos;
    }

    /* 마감 임박 펀딩  조회 */
    private List<FundingNoticeDto> fundingNotice(@AuthUser  User user) {

        List<Funding> deadlineFundings=  fundingRepository.findAllByUserId(user.getUserId());

        // 하루 남았거나 당일 마감인 펀딩 필터
        List<Funding> filteredFunding =  deadlineFundings.stream()
                .filter(funding -> {
                    LocalDate endDate = funding.getFundingEndDate();
                    return (endDate.equals(today)) || endDate.equals(today.plusDays(1));
                })
                .collect(Collectors.toList());

        List<FundingNoticeDto> fundingNoticeDtos = filteredFunding.stream()
                .map(funding -> FundingNoticeDto.from(funding))
                .collect(Collectors.toList());

        return fundingNoticeDtos;
    }

    /* 친구 알림 함수 조회 */
    public ResponseEntity<?> getFriendNotice(@AuthUser User user){

        List<FriendNoticeDto> friendNoticeDto = friendNotice(user);

        if(friendNoticeDto.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("친구 알림이 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(friendNoticeDto);
    }

    /* 펀딩 알림 조회 */
    public ResponseEntity<?> getFundingNotice(@AuthUser  User user){
        List<FundingNoticeDto> fundingNoticeDtos = fundingNotice(user);

        if(fundingNoticeDtos.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("펀딩 알림이 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(fundingNoticeDtos);
    }


    /* 펀딩 전체 알림 조회 */
    public ResponseEntity<?> getAllNotice(@AuthUser User user) {
        // 펀딩 알림
        List<FundingNoticeDto> fundingNoticeDtos = fundingNotice(user);
        // 친구 알림
        List<FriendNoticeDto> friendNoticeDto = friendNotice(user);

        AllNoticeDto allNoticeDto = AllNoticeDto.from(fundingNoticeDtos , friendNoticeDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allNoticeDto);
    }
}
