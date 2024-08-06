package efub.gift_u.domain.participation.service;

import efub.gift_u.domain.gift.repository.GiftRepository;
import efub.gift_u.domain.participation.domain.Participation;
import efub.gift_u.domain.participation.dto.*;
import efub.gift_u.domain.participation.repository.ParticipationRepository;
import efub.gift_u.global.exception.CustomException;
import efub.gift_u.global.exception.ErrorCode;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.repository.FundingRepository;
import efub.gift_u.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final FundingRepository fundingRepository;
    private final GiftRepository giftRepository;



    /* 특정 펀딩에 대한 기여자 조회 */
    public List<ParticipationResponseDto> getParticipationDetail(Funding funding){
        List<Participation> participationList= participationRepository.findByFunding(funding);
        //참여자가 없는 경우
        if(participationList.isEmpty()){
            //throw new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND , "해당 펀딩에는 참여자가 없습니다.");
            return null;
        }
        Collections.sort(participationList, Comparator.comparing(Participation::getContributionAmount).reversed());
        return ParticipationResponseDto.from(participationList);
    }

    /* 펀딩 참여 */
    public synchronized JoinResponseDto joinFunding(User user, Long fundingId, JoinRequestDto requestDto) {
           Funding funding = fundingRepository.findById(fundingId)
                   .orElseThrow(() -> new CustomException(ErrorCode.FUNDING_NOT_FOUND));
           // 펀딩 개최자와 참여자가 동일인물인지 확인
           if (Objects.equals(user.getUserId(), funding.getUser().getUserId())) {
                throw new CustomException(ErrorCode.INVALID_USER_PARTICIPATION);
           }

           // 펀딩 참여 횟수를 1회로 제한
           if(!participationRepository.findParticipationByUserIdAndFundingId(user.getUserId() , fundingId).isEmpty()){ //이미 펀딩에 참여했다면
               throw new CustomException(ErrorCode.INVALID_ACCESS);
           }

           Long toAddAmount = requestDto.getContributionAmount(); //funding 테이블의 nowMoney를 업데이트 하기 위해
           funding.updateNowMoney(toAddAmount);

           //가격 상한선 추가
           if(funding.getNowMoney() > giftRepository.findMaxPriceByFundingId(fundingId)){
               throw new CustomException(ErrorCode.OVER_MAX_LIMIT);
           }

           Participation Participation = JoinRequestDto.toEntity(user ,funding,
                   requestDto.getContributionAmount() , requestDto.getAnonymity(),  requestDto.getMessage());
           Participation savedParticipation = participationRepository.save(Participation);

           JoinResponseDto dto = JoinResponseDto.from(savedParticipation);
           return dto;
    }


    /* 펀딩 참여 취소 */
    public void cancelFundingParticipation(User user, Long participationId) {
        Participation participation = participationRepository.findByParticipationIdAndUserId(participationId, user.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND));

        Funding funding = participation.getFunding();
        funding.updateNowMoney(-participation.getContributionAmount());
        participationRepository.delete(participation);
    }

    /*펀딩 참여 익명성 및 메세지 수정*/
    public ResponseEntity<?> patchParticipationVisibilityAndMessage(User user, Long participationId , ModifyRequestDto modifyRequestDto) {
         Participation participation = participationRepository.findById(participationId)
                 .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND));

         if(!(participation.getUser().getUserId()).equals(user.getUserId())){ // 참여자 id와 요청 사용자 id가 다르다면
             throw  new CustomException(ErrorCode.INVALID_USER); // 올바르지 않은 접근
         }

        participation.updateVisibility(modifyRequestDto.getAnonymity());
         participation.updateMessage(modifyRequestDto.getMessage());

        JoinResponseDto modifyResponseDto = JoinResponseDto.from(participation);

        return ResponseEntity.status(HttpStatus.OK)
                .body(modifyResponseDto);
    }

    /* 내가 참여한 펀딩의 참여한 내역 조회*/
    public ResponseEntity<?> getMyParticipation(User user, Long fundingId) {
       Participation participation = participationRepository.findByUserIdAndFundingId(user.getUserId() , fundingId)
               .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND));

        VisibilityAndMessageResponseDto dto = VisibilityAndMessageResponseDto.from(participation);

        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }

}
