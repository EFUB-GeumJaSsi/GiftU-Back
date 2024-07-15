package efub.gift_u.funding.service;

import efub.gift_u.exception.CustomException;
import efub.gift_u.exception.ErrorCode;
import efub.gift_u.funding.domain.Funding;
import efub.gift_u.funding.domain.FundingStatus;
import efub.gift_u.funding.dto.*;
import efub.gift_u.funding.repository.FundingRepository;
import efub.gift_u.gift.domain.Gift;
import efub.gift_u.gift.repository.GiftRepository;
import efub.gift_u.participation.domain.Participation;
import efub.gift_u.participation.repository.ParticipationRepository;
import efub.gift_u.participation.service.ParticipationService;
import efub.gift_u.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FundingService {
    private final FundingRepository fundingRepository;
    private final GiftRepository giftRepository;
    private final ParticipationService participationService;
    private final ParticipationRepository participationRepository;

    public FundingResponseDto createFunding(User user, FundingRequestDto requestDto) {
        Long password = requestDto.getVisibility() ? null : requestDto.getPassword();
        Funding funding = Funding.builder()
                .user(user)
                .fundingTitle(requestDto.getFundingTitle())
                .fundingContent(requestDto.getFundingContent())
                .fundingStartDate(LocalDate.now())
                .fundingEndDate(requestDto.getFundingEndDate())
                .status(FundingStatus.IN_PROGRESS)
                .deliveryAddress(requestDto.getDeliveryAddress())
                .visibility(requestDto.getVisibility())
                .password(password)
                .nowMoney(0L)
                .fundingImageUrl(requestDto.getFundingImageUrl())
                .build();

        Funding savedFunding = fundingRepository.save(funding);

        List<Gift> gifts = requestDto.getGifts().stream()
                .map(giftDto -> Gift.builder()
                        .funding(savedFunding)
                        .giftName(giftDto.getGiftName())
                        .price(Long.valueOf(giftDto.getPrice()))
                        .giftUrl(giftDto.getGiftUrl())
                        .giftImageUrl(giftDto.getGiftImageUrl())
                        .build())
                .collect(Collectors.toList());

        savedFunding.getGiftList().addAll(gifts);
        giftRepository.saveAll(gifts);

        return FundingResponseDto.fromEntity(savedFunding);
    }


    /* 펀딩 상세 조회 */
    public ResponseEntity<FundingResponseDetailDto> getFundingDetail(Long fundingId) {
         Funding funding = fundingRepository.findById(fundingId)
                 .orElseThrow(() -> new CustomException(ErrorCode.FUNDING_NOT_FOUND));

        return  ResponseEntity.status(HttpStatus.OK)
                .body(FundingResponseDetailDto.from(funding ,  participationService.getParticipationDetail(funding)));
    }


    /* 펀딩 리스트 조회 - 내가 개설한 - 전체 */
    public AllFundingResponseDto getAllFundingByUser(Long userId) {
        List<Funding> fundings = fundingRepository.findAllByUserId(userId);
        List<IndividualFundingResponseDto> dtoList = fundings.stream()
            .map(IndividualFundingResponseDto::from)
            .collect(Collectors.toList());
        return new AllFundingResponseDto(dtoList);
    }

    /* 펀딩 리스트 조회 - 내가 개설한 - 상태 필터링 */
    public AllFundingResponseDto getAllFundingByUserAndStatus(Long userId, FundingStatus status) {
        List<Funding> fundings = fundingRepository.findAllByUserAndStatus(userId, status);
        List<IndividualFundingResponseDto> dtoList = fundings.stream()
            .map(IndividualFundingResponseDto::from)
            .collect(Collectors.toList());
        return new AllFundingResponseDto(dtoList);
    }


    /* 펀딩 리스트 조회 - 내가 참여한 - 전체 */
    public AllFundingResponseDto getAllParticipatedFundingByUser(Long userId) {
        List<Funding> fundings = participationRepository.findAllFundingByUserId(userId);
        List<IndividualFundingResponseDto> dtoList = fundings.stream()
                .map(IndividualFundingResponseDto::from)
                .collect(Collectors.toList());
        return new AllFundingResponseDto(dtoList);
    }

    /* 펀딩 리스트 조회 - 내가 참여한 - 상태 필터링 */
    public AllFundingResponseDto getAllParticipatedFundingByUserAndStatus(Long userId, FundingStatus status) {
        List<Funding> fundings = participationRepository.findAllFundingByUserIdAndStatus(userId, status);
        List<IndividualFundingResponseDto> dtoList = fundings.stream()
                .map(IndividualFundingResponseDto::from)
                .collect(Collectors.toList());
        return new AllFundingResponseDto(dtoList);
    }
}


