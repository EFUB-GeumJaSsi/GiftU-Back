package efub.gift_u.domain.pay.service;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.repository.FundingRepository;
import efub.gift_u.domain.participation.domain.Participation;
import efub.gift_u.domain.participation.repository.ParticipationRepository;
import efub.gift_u.domain.pay.domain.Pay;
import efub.gift_u.domain.pay.dto.Request.PayRequestDto;
import efub.gift_u.domain.pay.dto.Response.PayResponseDto;
import efub.gift_u.domain.pay.repository.PayRepository;
import efub.gift_u.domain.user.domain.User;
import efub.gift_u.global.exception.CustomException;
import efub.gift_u.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PayService {

    private final PayRepository paymentRepository;
    private final FundingRepository fundingRepository;
    private final ParticipationRepository participationRepository;

    /* 결제 내역  */
    public PayResponseDto createPayment(User user  , String paymentNumber , PayRequestDto paymentRequestDto , IamportResponse<Payment> iamportResponse) {
       Long userId = user.getUserId();
       Long fundingId = paymentRequestDto.getFundingId();
       Long resAmount = iamportResponse.getResponse().getAmount().longValue();
       // 펀딩 참여 테이블의 기여금액과 일치하는지 확인
        Participation participation = participationRepository.findByUserIdAndFundingId(userId , fundingId)
                .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND));
        if(participation.getContributionAmount() != resAmount || participation.getContributionAmount()!=paymentRequestDto.getAmount()){ // 결제 조회값이나 참여 테이블 기여금액과 다르면
            //금액이 다르다면 결제 취소
            throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
        }
        //기본키 값이 일치하는 게 있는지
        if(paymentRepository.countByPayIdContainingIgnoreCase(paymentNumber) !=0){ // 결제 번호가 겹치는 값이 있다면
            throw new IllegalArgumentException("결제 번호 오류");
        }

       Funding funding = fundingRepository.findByFundingId(paymentRequestDto.getFundingId());
       Pay pay = Pay.toEntity(paymentNumber ,user , funding , paymentRequestDto.getAmount());
       paymentRepository.save(pay);
       log.info("결제 성공 : 결제 번호 {}" , pay.getPayId());
       PayResponseDto payRes = PayResponseDto.from(pay.getPayId() , pay.getUser().getUserId() ,
               pay.getFunding().getFundingId() , pay.getAmount()
               );
       return  payRes;
    }
}
