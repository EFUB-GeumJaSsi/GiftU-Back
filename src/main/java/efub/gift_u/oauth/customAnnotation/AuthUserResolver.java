package efub.gift_u.oauth.customAnnotation;

import efub.gift_u.oauth.jwt.JwtService;
import efub.gift_u.user.domain.User;
import efub.gift_u.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// 2. @AuthUser 애노테이션이 적용된 파라미터를 처리하는 리졸버
@Component
@RequiredArgsConstructor
public class AuthUserResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    // 주어진 매개변수에 @Authuser 있는지, User 타입인지 확인하는 메소드 //
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class) &&
                User.class.isAssignableFrom(parameter.getParameterType());
    }

    // 해당 토큰 찾아서 userId 찾고, userId로 해당 user 객체 찾아 반환하는 메서드 //
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = resolveTokenFromRequest(webRequest);
        if (token == null) {
            throw new IllegalArgumentException("인증 해더의 토큰을 가져오지 못했습니다."); }
        Long userId = jwtService.extractUserId(token);
        if (userId == null) {
            throw new NullPointerException("해당 토큰을 가진 유저를 찾을 수 없습니다."); }
        return userRepository.findByUserId(userId);

    }

    // webRequest의 헤더의 토큰 값 가져오는 메소드 //
    private String resolveTokenFromRequest(NativeWebRequest webRequest) {
        String bearerToken = webRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

}
