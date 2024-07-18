package efub.gift_u.review.controller;

import efub.gift_u.oauth.customAnnotation.AuthUser;
import efub.gift_u.review.dto.ReviewRequestDto;
import efub.gift_u.review.dto.ReviewResponseDto;
import efub.gift_u.review.service.ReviewService;
import efub.gift_u.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /* 리뷰 생성 */
    @PostMapping("/fundings/{fundingId}/review")
    public ResponseEntity<?> createReview(@AuthUser User user ,@PathVariable("fundingId") Long fundingId , @RequestBody ReviewRequestDto requestDto){
        return reviewService.createReview(user ,fundingId , requestDto);
    }

    /* 리뷰 조회 */
    @GetMapping("/fundings/{fundingId}/review")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable("fundingId") Long fundingId){
        ReviewResponseDto dto = reviewService.findReview(fundingId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }

    /* 리뷰 수정 */
    @PatchMapping("/fundings/{fundingId}/review")
    public ResponseEntity<?>  patchReview(@AuthUser User user ,@PathVariable("fundingId") Long fundingId , @RequestBody ReviewRequestDto requestDto){
        return reviewService.updateReview(user ,fundingId , requestDto);
    }

    /* 리뷰 삭제 */
    @DeleteMapping("/fundings/{fundingId}/review")
    public  ResponseEntity<?> deleteReview(@AuthUser User user, @PathVariable("fundingId") Long fundingId){
        return reviewService.deleteReview(user ,fundingId);

    }

}
