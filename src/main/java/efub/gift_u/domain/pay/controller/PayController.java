package efub.gift_u.domain.pay.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import efub.gift_u.domain.oauth.customAnnotation.AuthUser;
import efub.gift_u.domain.pay.dto.Request.PayRequestDto;
import efub.gift_u.domain.pay.dto.Response.PayResponseDto;
import efub.gift_u.domain.pay.service.PayService;
import efub.gift_u.domain.pay.service.RefundService;
import efub.gift_u.domain.user.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;
    private final RefundService refundService;

    @Value("${portone.api.key}")
    private String apiKey;

    @Value("${portone.api.secretKey}")
    private String secretKey;
    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }

    /* 결제 검증 및 결제 내역 저장  */
    @PostMapping("/payment/{imp_uid}")
    public ResponseEntity<?> createPayment(@PathVariable("imp_uid") String imp_uid ,@AuthUser User user , @RequestBody PayRequestDto payRequestDto)
            throws IOException {
        // 결제 번호
        // String payNumber  = payService.generateMerchantUid(user);
        String payNumber = imp_uid;
        log.info("paymentByImpUid 진입 : {}" , imp_uid);
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(payNumber);

        log.info("결제 요청 응답. 결제 번호 :{}" ,iamportResponse.getResponse().getMerchantUid());
        try {
            PayResponseDto payResponseDto = payService.createPayment(user, payNumber ,payRequestDto , iamportResponse);
            log.info("결제 성공 ! 펀딩 참여 결제 번호 : {} " , payNumber);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(payResponseDto);
        }
        catch (RuntimeException e){
            log.info("펀딩 참여 결제 취소 : 펀딩 참여 결제 번호 {}" ,  payNumber);
            String token = refundService.getToken(apiKey , secretKey);
            refundService.refundRequest(token , payNumber , e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("결제 금액 불일치로 인한 결제 취소");
        }
    }


//    @PostMapping("/payment/{imp_uid}")
//    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid){
//        IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
//        log.info("결제 요청 응답. 결제 번호 :{}" , payment.getResponse().getMerchantUid());
//        return  payment;
//    }

}
