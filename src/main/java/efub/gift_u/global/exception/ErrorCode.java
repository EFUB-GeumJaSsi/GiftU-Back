package efub.gift_u.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Friend
    SELF_FRIEND_REQUEST_NOT_ALLOWED(HttpStatus.FORBIDDEN, "자기 자신에게 친구 요청을 보낼 수 없습니다."),
    FRIEND_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "친구 요청을 찾을 수 없습니다."),
    DUPLICATE_FRIEND_REQUEST(HttpStatus.BAD_REQUEST, "이미 친구 요청을 보냈습니다."),
    ACCEPT_AFTER_REJECT(HttpStatus.BAD_REQUEST, "이미 거절된 요청입니다."),
    REJECT_AFTER_ACCEPT(HttpStatus.BAD_REQUEST, "이미 수락된 요청입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    NOT_FRIEND(HttpStatus.BAD_REQUEST, "현재 친구가 아닙니다. 친구를 삭제할 수 없습니다."),

    // Auth
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    FAIL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "로그인 후 이용 가능합니다."), // 로그인X 유저의 요청 OR 토큰 불일치
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."), // 엑세스 토큰 만료
    FAIL_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."), // 권한 없는 요청
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"),// 예상치 못한 에러

    // Funding
    INVALID_PASSWORD_PATTERN(HttpStatus.BAD_REQUEST, "비밀번호는 4자리 숫자여야 합니다."),
    FUNDING_END_DATE_BEFORE_START(HttpStatus.BAD_REQUEST, "펀딩 종료일이 오늘을 우선할 수 없습니다. 펀딩 종료일을 오늘 이후로 설정해주세요."),
    FUNDING_NOT_FOUND(HttpStatus.NOT_FOUND , "해당 펀딩을 찾을 수 없습니다."),
    FUNDING_DELETE_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "펀딩 삭제 권한이 없습니다."),
    NO_GIFTS_FOUND(HttpStatus.NOT_FOUND, "선물을 찾을 수 없습니다."),

    // Participation
    PARTICIPATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 펀딩에 참여자가 존재하지 않습니다."),
    OVER_MAX_LIMIT(HttpStatus.BAD_REQUEST , "해당 펀딩은 이미 상한 금액에 도달하였습니다."),
    ALREADY_PARTICIPATED(HttpStatus.BAD_REQUEST , "해당 사용자는 이미 펀딩에 참여하였습니디"),
    INVALID_USER_PARTICIPATION(HttpStatus.FORBIDDEN , "펀딩 개설자와 참여자가 일치합니다."),
    INVALID_ACCESS(HttpStatus.FORBIDDEN , "해당 사용자는 펀딩에 참여할 권한이 없습니다."),


    // Review
    INVALID_USER(HttpStatus.FORBIDDEN , "저장된 소유자와 일치하지 않습니다."), // Participation에서도 사용
    ALREADY_EXIST(HttpStatus.BAD_REQUEST , "해당 펀딩에 대한 리뷰가 이미 존재합니다."),

    // Pay
    DUPLICATED_IMP(HttpStatus.BAD_REQUEST , "결제 번호가 중복됩니다."),
    INVALID_AMOUNT(HttpStatus.BAD_REQUEST , "결제 금액이 일치하지 않습니다."),

    // User
    USER_NOT_FOUND_BY_EMAIL(HttpStatus.NOT_FOUND, "해당 email를 가진 User를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}