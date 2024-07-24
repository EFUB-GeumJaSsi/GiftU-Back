package efub.gift_u.domain.search.service;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.repository.FundingRepository;
import efub.gift_u.domain.search.dto.SearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final FundingRepository fundingRepository;
    public ResponseEntity<?> Search(String searchWord) {
        List<Funding> funding = fundingRepository.fundByUserOrFundingTitleOrFundingContent(searchWord);
        List<SearchResponseDto> searchResponseDtos = funding.stream()
                .map(response -> SearchResponseDto.from(response))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(searchResponseDtos);
    }
}
