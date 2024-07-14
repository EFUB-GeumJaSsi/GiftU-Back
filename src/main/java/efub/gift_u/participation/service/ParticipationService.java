package efub.gift_u.participation.service;

import efub.gift_u.exception.CustomException;
import efub.gift_u.exception.ErrorCode;
import efub.gift_u.funding.domain.Funding;
import efub.gift_u.participation.domain.Participation;
import efub.gift_u.participation.dto.ParticipationResponseDto;
import efub.gift_u.participation.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;


    /* 특정 펀딩에 대한 기여자 조회 */
    public List<ParticipationResponseDto> getParticipationDetail(Funding funding){
        List<Participation> participationList= participationRepository.findByFunding(funding);
        //참여자가 없는 경우
        if(participationList.isEmpty()){
            throw new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND , "해당 펀딩에는 참여자가 없습니다.");
        }
        Collections.sort(participationList, Comparator.comparing(Participation::getContributionAmount).reversed());
        return ParticipationResponseDto.from(participationList);
    }

}
