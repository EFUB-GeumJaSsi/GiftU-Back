package efub.gift_u.domain.notice.service;

import efub.gift_u.domain.friend.domain.Friend;
import efub.gift_u.domain.friend.repository.FriendRepository;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.repository.FundingRepository;
import efub.gift_u.domain.gift.domain.Gift;
import efub.gift_u.domain.notice.dto.*;
import efub.gift_u.domain.oauth.customAnnotation.AuthUser;
import efub.gift_u.domain.participation.domain.Participation;
import efub.gift_u.domain.participation.repository.ParticipationRepository;
import efub.gift_u.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static efub.gift_u.domain.friend.domain.FriendStatus.PENDING_FIRST_SECOND;
import static efub.gift_u.domain.friend.domain.FriendStatus.PENDING_SECOND_FIRST;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final FriendRepository friendRepository;
    private final FundingRepository fundingRepository;
    private final ParticipationRepository participationRepository;

    LocalDate today = LocalDate.now(); //오늘 날짜
    LocalDateTime now = LocalDateTime.now(); // 조회하는 시간
    

    /* 친구 알림 조회 , (현재 요청 상태인 것만) 함수  */
    private List<FriendNoticeDto> friendNotice(@AuthUser User user){

        // user_id가 first user인 경우 ,first user가 받는 알림은 first user에게 친구 신청을 한 경우,  pending_second_first
        List<Friend> noticeAllByFirstUser=  friendRepository.findAllByFirstUserAndStatusWithFetch(user ,PENDING_SECOND_FIRST);
        List<FriendNoticeDto> firstNoticeDtos = noticeAllByFirstUser.stream()
                .map(notice -> FriendNoticeDto.firstFrom(notice))
                .collect(Collectors.toList());
        // user_id가 second user인 경우 , second user가 받는 알림은 second user에게 친구 신청을 한 경우 , pending_first_second
        List<Friend> noticeAllBySecondUser = friendRepository.findAllBySecondUserAndStatusWithFetch(user , PENDING_FIRST_SECOND);
        List<FriendNoticeDto> secondNoticeDtos = noticeAllBySecondUser.stream()
                .map(notice -> FriendNoticeDto.secondFrom(notice))
                .collect(Collectors.toList());

        firstNoticeDtos.addAll(secondNoticeDtos);
        // 친구 알림 목록을 updatedAt를 최신순으로 정렬 : Stream API
        List<FriendNoticeDto> sortedNotices = firstNoticeDtos.stream()
                .sorted(Comparator.comparing(FriendNoticeDto::getUpdatedAt).reversed())
                .collect(Collectors.toList());

        return sortedNotices;
    }

    /* 마감 임박 펀딩 조회 */
    private List<FundingDueNoticeDto> fundingDueNotice(@AuthUser  User user) {

        List<Funding> deadlineFundings=  fundingRepository.findAllByUserId(user.getUserId());

        // 하루 남았거나 당일 마감인 펀딩 필터
        List<Funding> filteredFunding =  deadlineFundings.stream()
                .filter(funding -> {
                    LocalDate endDate = funding.getFundingEndDate();
                    return (endDate.equals(today)) || endDate.equals(today.plusDays(1));
                })
                .collect(Collectors.toList());

        List<FundingDueNoticeDto> fundingDueNoticeDtos = filteredFunding.stream()
                .map(funding -> FundingDueNoticeDto.from(funding))
                .collect(Collectors.toList());

        return fundingDueNoticeDtos;
    }

    /*펀딩 달성도 조회*/
    private List<FundingAchieveDto> fundingAchieveNotice(@AuthUser User user){
            // 사용자가 개설한 펀딩
            List<Funding> allFunding = fundingRepository.findAllByUserId(user.getUserId());

            // 해당 펀딩의 현재 모인 금액 / 해당 펀딩의 최고액 선물 값의 백분율
            List<FundingAchieveDto> fundingAchieveDtos = allFunding.stream()
                    .map(funding -> {
                        double maxGiftPrice = funding.getGiftList().stream()
                                        .mapToDouble(Gift::getPrice)
                                        .max()
                                        .orElse(0.0);
                                double percent = maxGiftPrice>0? (funding.getNowMoney()/maxGiftPrice)*100 : 0.0;
                        // 해당 펀딩의 마지막 참여자가 참여한 시간
                        Optional<LocalDateTime> lastParticipateTime = participationRepository.findCreatedAtByUserIdAndFundingId(funding.getFundingId());
                        System.out.println("가장 마지막 참여자 : " + lastParticipateTime);
                        LocalDateTime lastParticipateTimeFinal = lastParticipateTime.orElse(null);
                          return FundingAchieveDto.from(funding , percent ,lastParticipateTimeFinal);
                            })
                    .collect(Collectors.toList());

            return fundingAchieveDtos;
    }

    /* 친구 알림 조회 함수 */
    public ResponseEntity<?> getFriendNotice(@AuthUser User user){

        List<FriendNoticeDto> friendNoticeDto = friendNotice(user);

        if(friendNoticeDto.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(friendNoticeDto);
    }

    /* 펀딩 알림 조회 */
    public ResponseEntity<?> getFundingNotice(@AuthUser  User user){
        List<FundingDueNoticeDto> fundingDueNoticeDtos = fundingDueNotice(user);
        List<FundingAchieveDto> fundingAchieveDtos = fundingAchieveNotice(user);
        FundingAllNoticeDto fundingAllNotices = FundingAllNoticeDto.from(now , fundingDueNoticeDtos , fundingAchieveDtos);
        
        if(fundingAllNotices.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("펀딩 알림이 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(fundingAllNotices);
    }


    /* 전체 알림 조회 */
    public ResponseEntity<?> getAllNotice(@AuthUser User user) {
        // 펀딩 알림 _ 펀딩 종료 관련
        List<FundingDueNoticeDto> fundingDueNoticeDtos = fundingDueNotice(user);
        //펀딩 알림 _ 펀딩 달성 퍼센트
        List<FundingAchieveDto> fundingAchieveDtos = fundingAchieveNotice(user);

        // 친구 알림
        List<FriendNoticeDto> friendNoticeDto = friendNotice(user);

        AllNoticeDto allNoticeDto = AllNoticeDto.from(now ,fundingDueNoticeDtos ,fundingAchieveDtos ,friendNoticeDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(allNoticeDto);
    }
}
