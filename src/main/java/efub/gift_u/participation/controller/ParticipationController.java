package efub.gift_u.participation.controller;


import efub.gift_u.oauth.customAnnotation.AuthUser;
import efub.gift_u.participation.dto.JoinRequestDto;
import efub.gift_u.participation.dto.JoinResponseDto;
import efub.gift_u.participation.service.ParticipationService;
import efub.gift_u.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    /* 펀딩 참여 */
    @PostMapping("/fundings/{fundingId}")
    public ResponseEntity<JoinResponseDto> joinFunding(@AuthUser User user , @PathVariable("fundingId") Long fundingId , @RequestBody JoinRequestDto joinRequestDto){
         JoinResponseDto dto = participationService.joinFunding(user , fundingId , joinRequestDto);
          return ResponseEntity.status(HttpStatus.CREATED)
                  .body(dto);
    }
}
