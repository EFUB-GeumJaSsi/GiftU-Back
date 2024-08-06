package efub.gift_u.domain.participation.dto;

import efub.gift_u.domain.participation.domain.Participation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisibilityAndMessageResponseDto {

    private Boolean anonymity;

    private String message;

    public  VisibilityAndMessageResponseDto(Boolean anonymity , String message){
        this.anonymity = anonymity;
        this.message = message;
    }

    public static VisibilityAndMessageResponseDto from(Participation participation){
        return new VisibilityAndMessageResponseDto(
                participation.getAnonymous(),
                participation.getMessage()
        );
    }
}
