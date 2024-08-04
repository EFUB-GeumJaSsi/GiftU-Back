package efub.gift_u.domain.participation.controller;


import efub.gift_u.domain.oauth.customAnnotation.AuthUser;
import efub.gift_u.domain.participation.dto.JoinRequestDto;
import efub.gift_u.domain.participation.dto.JoinResponseDto;
import efub.gift_u.domain.participation.dto.ModifyRequestDto;
import efub.gift_u.domain.participation.service.ParticipationService;
import efub.gift_u.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fundings")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    /* 펀딩 참여 */
    @PostMapping("/{fundingId}")
    public ResponseEntity<JoinResponseDto> joinFunding(@AuthUser User user , @PathVariable("fundingId") Long fundingId , @RequestBody JoinRequestDto joinRequestDto){
         JoinResponseDto dto = participationService.joinFunding(user , fundingId , joinRequestDto);
          return ResponseEntity.status(HttpStatus.CREATED)
                  .body(dto);
    }

    /* 펀딩 참여 취소 */
    @DeleteMapping("/participation/{participationId}")
    public ResponseEntity<String> cancelFundingParticipation(@AuthUser User user, @PathVariable("participationId") Long participationId) {
        participationService.cancelFundingParticipation(user, participationId);
        return ResponseEntity.ok("펀딩 참여가 성공적으로 취소되었습니다.");
    }

    /*펀딩 익명성 변경 및 축하 메세지 변경 */
    @PatchMapping("/participation/{participationId}")
    public ResponseEntity<?> patchParticipationVisibilityAndMessage(@AuthUser User user , @PathVariable("participationId") Long participationId , @RequestBody ModifyRequestDto modifyRequestDto){
       return participationService.patchParticipationVisibilityAndMessage(user , participationId , modifyRequestDto);
    }

    /*특정 펀딩의 사용자 참여 내역 조회*/
    @GetMapping("/{fundingId}/participation")
    public ResponseEntity<?> getMyParticipation(@AuthUser User user , @PathVariable("fundingId") Long fundingId){
        return participationService.getMyParticipation(user , fundingId);
    }
}
