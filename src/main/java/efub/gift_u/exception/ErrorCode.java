package efub.gift_u.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Auth
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    FAIL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "로그인 후 이용 가능합니다."), // 로그인X 유저의 요청 OR 토큰 불일치
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."), // 엑세스 토큰 만료
    FAIL_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."), // 권한 없는 요청
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"),// 예상치 못한 에러


    // Funding
    FUNDING_NOT_FOUND(HttpStatus.NOT_FOUND , "해당 펀딩을 찾을 수 없습니다."),

    // Participation
    PARTICIPATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 펀딩에 참여자가 존재하지 않습니다."),

    // Review
    INVALID_USER(HttpStatus.FORBIDDEN , "펀딩의 소유자와 일치하지 않습니다."),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST , "해당 펀딩에 대한 리뷰가 이미 존재합니다.");

    private final HttpStatus status;
    private final String message;
}