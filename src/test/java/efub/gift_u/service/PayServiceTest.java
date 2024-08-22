package efub.gift_u.service;

import efub.gift_u.domain.delivery.domain.Delivery;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.funding.domain.FundingStatus;
import efub.gift_u.domain.funding.repository.FundingRepository;
import efub.gift_u.domain.pay.domain.Pay;
import efub.gift_u.domain.pay.repository.PayRepository;
import efub.gift_u.domain.pay.service.PayService;
import efub.gift_u.domain.user.domain.User;
import efub.gift_u.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@SpringBootTest
@Transactional
public class PayServiceTest {

    @Autowired
    private PayService payService;

    @Autowired
    private PayRepository payRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FundingRepository fundingRepository;


    @Test
    @DisplayName("결제 내역 삭제 테스트")
    void testDeletePayment_InCancelPay(){

        //given
        LocalDate localDate = LocalDate.of(2100, 12, 31);
        Date birthday = java.sql.Date.valueOf(localDate);
        LocalDate testDay1 = LocalDate.of(2024, 8, 22);
        LocalDate testDay2 = LocalDate.of(2024, 10, 22);

        User user1 = User.builder()
                .nickname("테스트 객체")
                .email("abc@mail.com")
                .birthday(birthday)
                .userImageUrl("")
                .kakaoAccessToken("asdfgh")
                .build();

        userRepository.save(user1);

        Delivery delivery1 = Delivery.builder()
                .name("테스트")
                .addressDetail1("테스트구")
                .addressDetail2("테스트동")
                .phoneNumber("010010101")
                .addressNumber("12345")
                .build();

        Funding funding1 = Funding.builder()
                .user(user1)
                .fundingTitle("제목입니다.")
                .fundingContent("펀딩 내용입니다.")
                .fundingEndDate(testDay2)
                .fundingImageUrl("s3.example")
                .fundingStartDate(testDay1)
                .status(FundingStatus.IN_PROGRESS)
                .password("0000")
                .visibility(false)
                .nowMoney(0L)
                .delivery(delivery1)
                .build();

        fundingRepository.save(funding1);

        String imp_uid = "imp_1234";
        Pay pay = new Pay(imp_uid, user1, funding1, 100L);

        payRepository.save(pay);

        //when
        payService.deletePayment(imp_uid);

        //then
        Assertions.assertNull(payRepository.findByPayId(imp_uid));
    }
}
