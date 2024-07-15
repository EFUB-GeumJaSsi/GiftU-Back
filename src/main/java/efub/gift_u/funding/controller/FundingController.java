package efub.gift_u.funding.controller;

import efub.gift_u.funding.domain.FundingStatus;
import efub.gift_u.funding.dto.*;
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


    /* 펀딩 리스트 조회 - 내가 개설한 - 전체 */
    @GetMapping("/list")
    public AllFundingResponseDto getFundingByUser(@AuthUser User user) {
        return fundingService.getAllFundingByUser(user.getUserId());
    }

    /* 펀딩 리스트 조회 - 내가 개설한 - 상태 필터링 */
    @GetMapping("/list/{status}")
    public AllFundingResponseDto getFundingByUserAndStatus(@AuthUser User user, @PathVariable("status") FundingStatus status){
        return fundingService.getAllFundingByUserAndStatus(user.getUserId(), status);
    }


    /* 펀딩 리스트 조회 - 내가 참여한 - 전체 */
    @GetMapping("/participation")
    public AllFundingResponseDto getParticipatedFundingByUser(@AuthUser User user) {
        return fundingService.getAllParticipatedFundingByUser(user.getUserId());
    }

    /* 펀딩 리스트 조회 - 내가 참여한 - 상태 필터링 */
    @GetMapping("/participation/{status}")
    public AllFundingResponseDto getParticipatedFundingByUserAndStatus(@AuthUser User user, @PathVariable("status") FundingStatus status){
        return fundingService.getAllParticipatedFundingByUserAndStatus(user.getUserId(), status);
    }


}

