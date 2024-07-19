package efub.gift_u.domain.funding.controller;


import efub.gift_u.domain.funding.dto.*;
import efub.gift_u.domain.funding.service.FundingService;
import efub.gift_u.domain.funding.domain.FundingStatus;
import efub.gift_u.domain.oauth.customAnnotation.AuthUser;
import efub.gift_u.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.time.LocalDate;

@RestController
@RequestMapping("/fundings")
@RequiredArgsConstructor
public class FundingController {
    private final FundingService fundingService;

    //펀딩 생성
    @PostMapping
    public ResponseEntity<FundingResponseDto> createFunding(@AuthUser User user,
                                                            @RequestPart("fundingRequestDto") FundingRequestDto requestDto,
                                                            @RequestPart(value = "giftImages", required = false) List<MultipartFile> giftImages) {
        FundingResponseDto createdFunding = fundingService.createFunding(user, requestDto, giftImages);
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


    /* 펀딩 리스트 조회 - 친구가 개설한 펀딩 중 진행 중인 */
    @GetMapping("/friends")
    public AllFundingResponseDto getFriendsFundingByUser(@AuthUser User user) {
        return fundingService.getAllFriendsFundingByUser(user);
    }


    /* 마감일 펀딩 목록 조회 - 캘린더 */
    @GetMapping("/calendar/{fundingEndDate}")
    public AllFundingResponseDto getFriendsFundingByUser(@AuthUser User user, @PathVariable("fundingEndDate")LocalDate fundingEndDate) {
        return fundingService.getAllFriendsFundingByUserAndDate(user, fundingEndDate);
    }


    /* 펀딩 비밀번호 확인*/
    @PostMapping("/{fundingId}/allow")
    public ResponseEntity<Boolean> isAllowed( @PathVariable("fundingId") Long fundingId , @RequestBody FundingPasswordDto fundingPasswordDto){
        if(fundingService.isAllowed(fundingId , fundingPasswordDto)){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(true);
        }
        else  return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(false);
    }

    //펀딩 삭제
    @DeleteMapping("/{fundingId}")
    public ResponseEntity<String> deleteFunding(@AuthUser User user, @PathVariable Long fundingId){
        fundingService.deleteFunding(fundingId, user);
        return ResponseEntity.ok("펀딩이 성공적으로 삭제되었습니다.");
    }

}


