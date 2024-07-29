package efub.gift_u.domain.participation.dto;


import efub.gift_u.domain.participation.domain.Participation;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinResponseDto {

    private Long participationId;
    private Long userId;
    private Long fundingId;
    private Long contributionAmount;
    private Boolean anonymous;
    private String message;
    private LocalDateTime created_at;

    public JoinResponseDto(Long participationId , Long userId , Long fundingId , Long contributionAmount , Boolean anonymous , String message , LocalDateTime created_at){
        this.participationId = participationId;
        this.userId = userId;
        this.fundingId = fundingId;
        this.contributionAmount = contributionAmount;
        this.anonymous = anonymous;
        this.message = message;
        this.created_at = created_at;
    }


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
