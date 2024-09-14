package efub.gift_u.domain.pay.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.repository.FundingRepository;
import efub.gift_u.domain.pay.domain.Pay;
import efub.gift_u.domain.pay.dto.Request.PayRequestDto;
import efub.gift_u.domain.pay.dto.Response.PayResponseDto;
import efub.gift_u.domain.pay.repository.PayRepository;
import efub.gift_u.domain.user.domain.User;
import efub.gift_u.global.exception.CustomException;
import efub.gift_u.global.exception.ErrorCode;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PayService {

    private final PayRepository paymentRepository;
    private final FundingRepository fundingRepository;
    private final PayRepository payRepository;

    @Value("${portone.api.key}")
    private String apiKey;

    @Value("${portone.api.secretKey}")
    private String secretKey;
    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    /* 결제 내역을 검증하고 DB에 결제 내역을 저장  */
    public PayResponseDto createPayment(User user  , String paymentNumber , PayRequestDto paymentRequestDto) {
       // Long userId = user.getUserId();
       //Long fundingId = paymentRequestDto.getFundingId();
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(paymentNumber); // 결제 번호 가져오기
        log.info("결제 요청 응답. 결제 번호 :{}" ,iamportResponse.getResponse().getMerchantUid());
        Long resAmount = iamportResponse.getResponse().getAmount().longValue();

       // 펀딩 참여 테이블의 기여금액과 일치하는지 확인 _ 참여 테이블의 기여 금액과 같은지 확인하는 과정 생략
//        Participation participation = participationRepository.findByUserIdAndFundingId(userId , fundingId)
//                .orElseThrow(() -> new CustomException(ErrorCode.PARTICIPATION_NOT_FOUND));
        //if(participation.getContributionAmount() != resAmount || participation.getContributionAmount()!=paymentRequestDto.getAmount()){ // 결제 조회값이나 참여 테이블 기여금액과 다르면

        if(!resAmount.equals(paymentRequestDto.getAmount())){ // 포트원에서 조회해온 금액이랑 프론트에서 받은 금액이 같은지 확인,
            //금액이 다르다면 결제 취소
            log.info("금액이 일치하지 않음");
            throw new CustomException(ErrorCode.INVALID_AMOUNT);

        }

        //기본키 값이 일치하는 게 있는지
        if(paymentRepository.countByPayIdContainingIgnoreCase(paymentNumber) !=0){ // 결제 번호가 겹치는 값이 있다면
            throw new CustomException(ErrorCode.DUPLICATED_IMP);
        }

       Funding funding = fundingRepository.findByFundingId(paymentRequestDto.getFundingId());
       Pay pay = Pay.toEntity(paymentNumber ,user , funding , paymentRequestDto.getAmount());
       paymentRepository.save(pay);
       PayResponseDto payRes = PayResponseDto.from(pay.getPayId() , pay.getUser().getUserId() ,
               pay.getFunding().getFundingId() , pay.getAmount()
               );
       return  payRes;
    }

    // iamportclient를 이용한 포트원 서버로 결제 취소 요청
    public String cancelPayment(String imp_uid){
        try{
            CancelData cancelData= new CancelData(imp_uid , true);
            IamportResponse<Payment> payment = iamportClient.cancelPaymentByImpUid(cancelData);

            log.info("{} " , payment.getMessage());
            if(payment.getMessage() != null && payment.getMessage().trim().equals("취소할 결제건이 존재하지 않습니다.")){
                 return "포트원에 해당 결제건이 존재하지 않습니다.";
            }
            else if(payment.getMessage() != null && payment.getMessage().trim().equals("이미 전액취소된 주문입니다.")){
                return "이미 취소한 결제건입니다.";
            }
                deletePayment(imp_uid);
                return "해당 결제가 취소되었습니다.";
        } catch (Exception e){
            return "결제 취소를 실패하였습니다.";
        }
    }

    public void deletePayment(String imp_uid){
        Pay pay = payRepository.findByPayId(imp_uid);
        log.info("삭제된 pay : {}" , pay);
        if(pay == null){
            throw new CustomException(ErrorCode.PAYMENT_NOT_FOUND);
        }
        payRepository.delete(pay);
    }
}
