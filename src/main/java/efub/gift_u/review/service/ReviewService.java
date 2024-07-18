package efub.gift_u.review.service;

import efub.gift_u.exception.CustomException;
import efub.gift_u.exception.ErrorCode;
import efub.gift_u.exception.ErrorResponse;
import efub.gift_u.funding.domain.Funding;
import efub.gift_u.funding.repository.FundingRepository;
import efub.gift_u.review.domain.Review;
import efub.gift_u.review.dto.ReviewRequestDto;
import efub.gift_u.review.dto.ReviewResponseDto;
import efub.gift_u.review.repository.ReviewRepository;
import efub.gift_u.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final FundingRepository fundingRepository;
    private final ReviewRepository reviewRepository;

    /*펀딩 id로 펀딩 찾기 */
    private Funding findFunding(Long fundingId){
        Funding targetFunding = fundingRepository.findById(fundingId)
                .orElseThrow(()-> new CustomException(ErrorCode.FUNDING_NOT_FOUND));
        return targetFunding;
    }

    /* 리뷰가 이미 존재하는지 확인 */
    private void checkReviewExistence(Funding funding) {
        if (isAlreadyExisted(funding)) {
            throw new CustomException(ErrorCode.ALREADY_EXIST);
        }
    }

    /* 펀딩 소유자와 리뷰 작성자 일치 여부 확인 */
    private void checkUserValidity(User user, Funding funding) {
        System.out.println(user.getUserId());
        System.out.println(funding.getUser().getUserId());
        System.out.println(Objects.equals(user.getUserId(), funding.getUser().getUserId()));
        if (!Objects.equals(user.getUserId(), funding.getUser().getUserId())) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
    }

    /* 리뷰 생성 */
    public ResponseEntity<?> createReview(User user , Long fundingId, ReviewRequestDto requestDto) {
        Funding funding = findFunding(fundingId);
        checkReviewExistence(funding);
        checkUserValidity(user , funding);
        Review Review = ReviewRequestDto.toEntity(funding , requestDto);
        Review savedReview = reviewRepository.save(Review);
        ReviewResponseDto dto = ReviewResponseDto.from(savedReview);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }

    /* 해당 펀딩에 대한 리뷰가 이미 존재하는지 확인 */
    public Boolean isAlreadyExisted(Funding funding){
        return reviewRepository.existsReviewByFunding(funding);
    }

    /*리뷰 조회*/
    public ReviewResponseDto findReview(Long fundingId) {
        Funding funding = findFunding(fundingId);
        Review review = reviewRepository.findByFunding(funding);
        ReviewResponseDto dto = ReviewResponseDto.from(review);
        return dto;
    }

    /* 리뷰 수정 */
    public ResponseEntity<?> updateReview(User user, Long fundingId, ReviewRequestDto requestDto) {
        Funding funding = findFunding(fundingId);
        checkUserValidity(user , funding);
        Review review = reviewRepository.findByFunding(funding);
        Review updateReview = review.update(review , requestDto);
        ReviewResponseDto dto = ReviewResponseDto.from(updateReview);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }

    /*리뷰 삭제*/
    public ResponseEntity<?> deleteReview(User user  , Long fundingId){
        Funding funding = findFunding(fundingId);
        checkUserValidity(user , funding);
        Review review = reviewRepository.findByFunding(funding);
        reviewRepository.delete(review);
        return ResponseEntity.status(HttpStatus.OK)
                .body("리뷰가 정상적으로 삭제되었습니다.");
    }

}
