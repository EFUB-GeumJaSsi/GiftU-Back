package efub.gift_u.domain.participation.dto;

import efub.gift_u.domain.participation.domain.Participation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipationResponseDto {


     private Long participationId;
     private String nickname;
     private String userImageUrl;
     private Boolean anonymous;
     private LocalDateTime created_at;
     private Long contributionAmount;
     private String message;

     public ParticipationResponseDto(Long participationId , String nickname , String  userImageUrl , Boolean anonymous ,
                                     LocalDateTime created_at , Long contributionAmount , String message){
         this.participationId = participationId;
         this.nickname = nickname;
         this.userImageUrl = userImageUrl;
         this.anonymous = anonymous;
         this.created_at = created_at;
         this.contributionAmount = contributionAmount;
         this.message = message;
     }

     public static List<ParticipationResponseDto> from(List<Participation> participations){

        List<ParticipationResponseDto> dtos = new ArrayList<>();
        for (Participation participation : participations) {
             ParticipationResponseDto dto = new ParticipationResponseDto(
                     participation.getParticipationId(),
                     participation.getUser().getNickname(),
                     participation.getUser().getUserImageUrl(),
                     participation.getAnonymous(),
                     participation.getCreatedAt(),
                     participation.getContributionAmount(),
                     participation.getMessage()
             );

             dtos.add(dto);
         }
        return dtos;
     }


}
