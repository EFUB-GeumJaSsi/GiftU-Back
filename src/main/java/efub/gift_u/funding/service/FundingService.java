package efub.gift_u.funding.service;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.funding.domain.FundingStatus;
import efub.gift_u.funding.dto.FundingRequestDto;
import efub.gift_u.funding.dto.FundingResponseDto;
import efub.gift_u.funding.repository.FundingRepository;
import efub.gift_u.gift.domain.Gift;
import efub.gift_u.gift.repository.GiftRepository;
import efub.gift_u.user.domain.User;
import lombok.RequiredArgsConstructor;
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
}


