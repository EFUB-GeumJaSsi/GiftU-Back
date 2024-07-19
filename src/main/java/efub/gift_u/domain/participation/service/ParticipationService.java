package efub.gift_u.domain.participation.service;

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
        Collections.sort(participationList, Comparator.comparing(Participation::getContributionAmount).reversed());
        return ParticipationResponseDto.from(participationList);
    }

    /* 펀딩 참여 */
    public JoinResponseDto joinFunding(User user, Long fundingId, JoinRequestDto requestDto) {
           Funding funding = fundingRepository.findById(fundingId)
                   .orElseThrow(() -> new CustomException(ErrorCode.FUNDING_NOT_FOUND));
           Participation Participation = JoinRequestDto.toEntity(user , funding ,
                   requestDto.getContributionAmount() , requestDto.getAnonymity(),  requestDto.getMessage());
           Participation savedParticipation = participationRepository.save(Participation);
           JoinResponseDto dto = JoinResponseDto.from(savedParticipation);
           return dto;
    }
}
