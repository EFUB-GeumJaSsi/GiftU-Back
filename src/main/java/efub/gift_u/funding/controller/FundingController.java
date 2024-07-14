package efub.gift_u.funding.controller;

import efub.gift_u.funding.dto.FundingRequestDto;
import efub.gift_u.funding.dto.FundingResponseDetailDto;
import efub.gift_u.funding.dto.FundingResponseDto;
import efub.gift_u.funding.service.FundingService;
import efub.gift_u.oauth.customAnnotation.AuthUser;
import efub.gift_u.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fundings")
@RequiredArgsConstructor
public class FundingController {
    private final FundingService fundingService;

    @PostMapping
    public ResponseEntity<FundingResponseDto> createFunding(@AuthUser User user, @RequestBody FundingRequestDto requestDto) {
        FundingResponseDto createdFunding = fundingService.createFunding(user, requestDto);
        return ResponseEntity.ok(createdFunding);
    }

    /*펀딩 상세 조회*/
    @GetMapping("/{fundingId}")
    public ResponseEntity<FundingResponseDetailDto> getFundingDetail(@PathVariable("fundingId") Long fundingId){
        return fundingService.getFundingDetail(fundingId);
    }
}

