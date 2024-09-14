package efub.gift_u.domain.pay.controller;

import efub.gift_u.domain.oauth.customAnnotation.AuthUser;
import efub.gift_u.domain.pay.dto.Request.PayRequestDto;
import efub.gift_u.domain.pay.dto.Response.PayResponseDto;
import efub.gift_u.domain.pay.service.PayService;
import efub.gift_u.domain.user.domain.User;
import efub.gift_u.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    /* 결제 검증 및 결제 내역 저장  */
    @PostMapping("/payment/{imp_uid}")
    public ResponseEntity<?> createPayment(@PathVariable("imp_uid") String imp_uid ,@AuthUser User user , @RequestBody PayRequestDto payRequestDto)
            throws IOException {
        // 결제 번호
        String payNumber = imp_uid;
        log.info("결제 시작 : {}" , payNumber);
        //IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(payNumber);
        //log.info("결제 요청 응답. 결제 번호 :{}" ,iamportResponse.getResponse().getMerchantUid());
        try {
            PayResponseDto payResponseDto = payService.createPayment(user, payNumber ,payRequestDto);
            log.info("결제 성공 ! 펀딩 참여 결제 번호 : {} " , payNumber);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(payResponseDto);
        }
        catch (CustomException e){
            log.info("펀딩 참여 결제 취소 : 펀딩 참여 결제 번호 {}" ,  payNumber);
           return  ResponseEntity.status(HttpStatus.OK)
                           .body(payService.cancelPayment(payNumber));
        }
    }

    /* 결제 취소 요청 */
    @PostMapping("/payment/cancel/{imp_uid}")
    public ResponseEntity<?> cancelPayment(@PathVariable("imp_uid") String imp_uid ){
        log.info("펀딩 결제 취소 : 펀딩 참여 결제 번호 : {}" , imp_uid );
        return ResponseEntity.status(HttpStatus.OK)
                .body(payService.cancelPayment(imp_uid));
    }

}
