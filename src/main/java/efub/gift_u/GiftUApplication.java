package efub.gift_u;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GiftUApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiftUApplication.class, args);
	}

}
