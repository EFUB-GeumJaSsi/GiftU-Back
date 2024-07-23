package efub.gift_u.domain.participation.service;

import efub.gift_u.domain.notice.dto.FriendNoticeDto;
import efub.gift_u.domain.participation.domain.Participation;
import efub.gift_u.domain.participation.dto.JoinRequestDto;
import efub.gift_u.domain.participation.dto.ParticipationResponseDto;
import efub.gift_u.domain.participation.repository.ParticipationRepository;
import efub.gift_u.global.exception.CustomException;
import efub.gift_u.global.exception.ErrorCode;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.repository.FundingRepository;
import efub.gift_u.domain.participation.dto.JoinResponseDto;
import efub.gift_u.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final FundingRepository fundingRepository;


    /* 특정 펀딩에 대한 기여자 조회 */
    public List<ParticipationResponseDto> getParticipationDetail(Funding funding){
        List<Participation> participationList= participationRepository.findByFunding(funding);
        //참여자가 없는 경우
        if(participationList.isEmpty()){
            //throw new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND , "해당 펀딩에는 참여자가 없습니다.");
            return null;
        }
        // 기여 금액대로 참여자 정렬 : Stream API
        List<Participation> sortedParticipation = participationList.stream()
                .sorted(Comparator.comparing(Participation::getContributionAmount).reversed())
                .collect(Collectors.toList());

        return ParticipationResponseDto.from(participationList);
    }

    /* 펀딩 참여 */
    public JoinResponseDto joinFunding(User user, Long fundingId, JoinRequestDto requestDto) {
           Funding funding = fundingRepository.findById(fundingId)
                   .orElseThrow(() -> new CustomException(ErrorCode.FUNDING_NOT_FOUND));
           Long toAddAmount = requestDto.getContributionAmount(); //funding 테이블의 nowMoney를 업데이트 하기 위해
           funding.updateNowMoney(toAddAmount);
           Participation Participation = JoinRequestDto.toEntity(user , funding ,
                   requestDto.getContributionAmount() , requestDto.getAnonymity(),  requestDto.getMessage());
           Participation savedParticipation = participationRepository.save(Participation);

           JoinResponseDto dto = JoinResponseDto.from(savedParticipation);
           return dto;
    }
}
