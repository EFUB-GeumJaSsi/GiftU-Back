package efub.gift_u.participation.dto;

import efub.gift_u.funding.domain.Funding;
import efub.gift_u.participation.domain.Participation;
import efub.gift_u.user.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinResponseDto {

    private Long participationId;
    private Long userId;
    private Long fundingId;
    private Long contributionAmount;
    private Boolean anonymous;
    private String message;
    private LocalDateTime created_at;

    public static JoinResponseDto from(Participation participation){
        return new JoinResponseDto(
                participation.getParticipationId(),
                participation.getUser().getUserId(),
                participation.getFunding().getFundingId(),
                participation.getContributionAmount(),
                participation.getAnonymous(),
                participation.getMessage(),
                participation.getCreatedAt()
        );
    }

}
