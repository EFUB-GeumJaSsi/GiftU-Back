package efub.gift_u.domain.participation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyRequestDto {

    private Boolean anonymity;

    private String message;
}
