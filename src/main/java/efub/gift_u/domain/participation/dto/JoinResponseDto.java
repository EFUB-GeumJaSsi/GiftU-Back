package efub.gift_u.domain.participation.dto;


import efub.gift_u.domain.participation.domain.Participation;
import lombok.*;


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
