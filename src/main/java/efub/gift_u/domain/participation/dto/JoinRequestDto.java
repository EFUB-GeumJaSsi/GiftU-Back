package efub.gift_u.domain.participation.dto;

import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.participation.domain.Participation;
import efub.gift_u.domain.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequestDto {


    @NotNull(message = "기여금액은 필수입니다.")
    private Long contributionAmount;
    // 익명성
    @NotNull(message = "익명성은 필수입니다.")
    private Boolean anonymity;

    private String message;

    public  JoinRequestDto(Long contributionAmount, Boolean anonymity , String message){
        this.contributionAmount = contributionAmount;
        this.anonymity = anonymity;
        this.message = message;
    }

    public static Participation toEntity(User user , Funding funding , Long contributionAmount , Boolean anonymity , String message){
        return Participation.builder()
                .user(user)
                .funding(funding)
                .contributionAmount(contributionAmount)
                .anonymous(anonymity)
                .message(message)
                .build();
    }

}
