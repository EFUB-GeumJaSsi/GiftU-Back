package efub.gift_u.domain.pay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class RefundService {

    private final RestTemplate restTemplate;

    public String getToken(String apiKey, String secretKey) {
        String url = "https://api.iamport.kr/users/getToken";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // 요청 바디 설정
        Map<String, String> body = new HashMap<>();
        body.put("imp_key", apiKey);
        body.put("imp_secret", secretKey);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // POST 요청 보내기
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
        Map<String, Object> responseBody = response.getBody();
        Map<String, Object> responseMap = (Map<String, Object>) responseBody.get("response");
        String accessToken = (String) responseMap.get("access_token");

        log.info("Iamport 엑세스 토큰 발급 성공 : ", accessToken);
        return accessToken;
    }
    public void refundRequest(String token, String paymentNumber, String message) {
        String url = "https://api.iamport.kr/payments/cancel";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);

        // 요청 바디 설정
        Map<String, String> body = new HashMap<>();
        body.put("merchant_uid", paymentNumber);
        body.put("reason", message);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        log.info("결제 취소 완료 , 결제 번호  {}", paymentNumber);
        //return response;
    }

}
