package efub.gift_u.global.config;

import efub.gift_u.domain.funding.service.FundingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@EnableScheduling
@Component
@RequiredArgsConstructor
public class SchedulerConfig {
    private final FundingService fundingService;

    @Scheduled(cron = "0 0 0 * * *") //매일 자정마다 실행
    public void updateFundingStatusDaily(){
        fundingService.updateFundingStatus();
    }
}
