package efub.gift_u.participation.dto;

import efub.gift_u.participation.domain.Participation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipationResponseDto {


     private Long participationId;
     private Long userId;
     private Boolean anonymous;
     private LocalDateTime created_at;
     private Long contributionAmount;
     private String message;

     public static List<ParticipationResponseDto> from(List<Participation> participations){

        List<ParticipationResponseDto> dtos = new ArrayList<>();
        for (Participation participation : participations) {
             ParticipationResponseDto dto = new ParticipationResponseDto(
                     participation.getParticipationId(),
                     participation.getUser().getUserId(),
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
