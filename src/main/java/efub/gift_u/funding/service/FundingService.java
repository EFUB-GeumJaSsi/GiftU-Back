package efub.gift_u.funding.service;

import efub.gift_u.S3Image.service.S3ImageService;
import efub.gift_u.exception.CustomException;
import efub.gift_u.exception.ErrorCode;
import efub.gift_u.friend.dto.FriendDetailDto;
import efub.gift_u.friend.dto.FriendListResponseDto;
import efub.gift_u.friend.service.FriendService;
import efub.gift_u.funding.domain.Funding;
import efub.gift_u.funding.domain.FundingStatus;
import efub.gift_u.funding.dto.*;
import efub.gift_u.funding.repository.FundingRepository;
import efub.gift_u.gift.domain.Gift;
import efub.gift_u.gift.repository.GiftRepository;
import efub.gift_u.participation.repository.ParticipationRepository;
import efub.gift_u.participation.service.ParticipationService;
import efub.gift_u.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static efub.gift_u.funding.domain.FundingStatus.IN_PROGRESS;

@Service
@Transactional
@RequiredArgsConstructor
public class FundingService {
    private final FundingRepository fundingRepository;
    private final GiftRepository giftRepository;
    private final ParticipationService participationService;
    private final ParticipationRepository participationRepository;
    private final FriendService friendService;
    private final S3ImageService s3ImageService;

    public FundingResponseDto createFunding(User user, FundingRequestDto requestDto, List<MultipartFile> giftImages) {

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
                .map(giftDto -> {
                    String giftImageUrl = null;
                    int index = requestDto.getGifts().indexOf(giftDto);
                    if (giftImages != null && !giftImages.isEmpty() && giftImages.size() > index) {
                        MultipartFile giftImage = giftImages.get(index);
                        if (giftImage != null && !giftImage.isEmpty()) {
                            try {
                                String giftFileName = s3ImageService.upload(giftImage, "images/giftImages"); // S3에 이미지 업로드
                                giftImageUrl = s3ImageService.getFileUrl(giftFileName); // 업로드된 파일의 URL 가져오기
                            } catch (IOException e) {
                                throw new RuntimeException("Gift image upload failed", e);
                            }
                        }
                    }

                    return Gift.builder()
                            .funding(savedFunding)
                            .giftName(giftDto.getGiftName())
                            .price(Long.valueOf(giftDto.getPrice()))
                            .giftUrl(giftDto.getGiftUrl())
                            .giftImageUrl(giftImageUrl)
                            .build();
                })
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
        List<IndividualFundingResponseDto> dtoList = convertToDtoList(fundings);
        return new AllFundingResponseDto(dtoList);
    }

    /* 펀딩 리스트 조회 - 내가 개설한 - 상태 필터링 */
    public AllFundingResponseDto getAllFundingByUserAndStatus(Long userId, FundingStatus status) {
        List<Funding> fundings = fundingRepository.findAllByUserAndStatus(userId, status);
        List<IndividualFundingResponseDto> dtoList = convertToDtoList(fundings);
        return new AllFundingResponseDto(dtoList);
    }


    /* 펀딩 리스트 조회 - 내가 참여한 - 전체 */
    public AllFundingResponseDto getAllParticipatedFundingByUser(Long userId) {
        List<Funding> fundings = participationRepository.findAllFundingByUserId(userId);
        List<IndividualFundingResponseDto> dtoList = convertToDtoList(fundings);
        return new AllFundingResponseDto(dtoList);
    }

    /* 펀딩 리스트 조회 - 내가 참여한 - 상태 필터링 */
    public AllFundingResponseDto getAllParticipatedFundingByUserAndStatus(Long userId, FundingStatus status) {
        List<Funding> fundings = participationRepository.findAllFundingByUserIdAndStatus(userId, status);
        List<IndividualFundingResponseDto> dtoList = convertToDtoList(fundings);
        return new AllFundingResponseDto(dtoList);
    }


    /* 펀딩 리스트 조회 - 친구가 개설한 펀딩 중 진행중인 펀딩 */
    public AllFundingResponseDto getAllFriendsFundingByUser(User user) {
        FriendListResponseDto friendList = friendService.getFriends(user);
        List<FriendDetailDto> friends = friendList.getFriends();
        List<Funding> fundings = new ArrayList<>();
        for (FriendDetailDto friend : friends) {
            fundings.addAll(fundingRepository.findAllByUserAndStatus(friend.getFriendId(), IN_PROGRESS));
        }
        List<IndividualFundingResponseDto> dtoList = convertToDtoList(fundings);
        return new AllFundingResponseDto(dtoList);
    }


    /* 해당 마감일 펀딩 목록 조회 - 캘린더 */
    public AllFundingResponseDto getAllFriendsFundingByUserAndDate(User user, LocalDate fundingEndDate) {
        FriendListResponseDto friendList = friendService.getFriends(user);
        List<FriendDetailDto> friends = friendList.getFriends();
        List<Funding> fundings = new ArrayList<>();
        for (FriendDetailDto friend : friends) {
            fundings.addAll(fundingRepository.findAllByUserAndFundingEndDate(friend.getFriendId(), fundingEndDate));
        }
        List<IndividualFundingResponseDto> dtoList = convertToDtoList(fundings);
        return new AllFundingResponseDto(dtoList);
    }


    private List<IndividualFundingResponseDto> convertToDtoList(List<Funding> fundings){
        return fundings.stream()
                .map(IndividualFundingResponseDto::from)
                .collect(Collectors.toList());
    }


    /* 펀딩 비밀번호 확인 */
    public Boolean isAllowed(Long fundingId, FundingPasswordDto fundingPasswordDto) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new CustomException(ErrorCode.FUNDING_NOT_FOUND));
        Long compPassword = fundingPasswordDto.getPassword();
        if(compPassword.equals(funding.getPassword())){
            return true;
        }
        else {
            return false;
        }
    }

}


