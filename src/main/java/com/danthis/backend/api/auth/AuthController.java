package com.danthis.backend.api.auth;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.auth.util.TokenCookieManager;
import com.danthis.backend.api.auth.util.TokenExtractor;
import com.danthis.backend.application.auth.AuthService;
import com.danthis.backend.application.auth.request.TokenServiceRequest;
import com.danthis.backend.application.auth.response.TokenServiceResponse;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증 관리 기능", description = "인증 관리 API 입니다.")
public class AuthController {

  private final AuthService authService;
  private final TokenExtractor tokenExtractor;
  private final TokenCookieManager tokenCookieManager;

  @Operation(summary = "카카오 로그인 API")
  @PostMapping("/login/kakao")
  public ApiResponse<TokenServiceResponse> loginKakao(
      @RequestParam(name = "code") String code,
      HttpServletResponse response) throws JsonProcessingException {
    TokenServiceResponse tokenServiceResponse = authService.kakaoLogin(code);
    tokenCookieManager.addRefreshTokenCookie(response, tokenServiceResponse.getRefreshToken());
    return ApiResponse.OK(tokenServiceResponse.withoutRefreshToken());
  }

  @Operation(summary = "토큰 재발급 API")
  @GetMapping("/reissue")
  public ApiResponse<TokenServiceResponse> reissueToken(
      HttpServletRequest request,
      HttpServletResponse response) {
    TokenServiceRequest tokenServiceRequest = tokenExtractor.extractTokenRequest(request);
    TokenServiceResponse tokenServiceResponse = authService.reissueAccessToken(tokenServiceRequest);
    tokenCookieManager.addRefreshTokenCookie(response, tokenServiceResponse.getRefreshToken());
    return ApiResponse.OK(tokenServiceResponse.withoutRefreshToken());
  }

  @Operation(summary = "로그아웃 API")
  @PostMapping("/logout")
  public ApiResponse<Void> logout(
      HttpServletRequest request,
      HttpServletResponse response) {
    TokenServiceRequest tokenServiceRequest = tokenExtractor.extractTokenRequest(request);
    authService.logout(tokenServiceRequest);
    tokenCookieManager.removeRefreshTokenCookie(response);
    return ApiResponse.OK(null);
  }

  @Operation(summary = "회원 탈퇴 API")
  @AssignCurrentUserInfo
  @DeleteMapping("/withdraw")
  public ApiResponse<Void> withdraw(
      CurrentUserInfo userInfo,
      HttpServletRequest request,
      HttpServletResponse response) {
    TokenServiceRequest tokenServiceRequest = tokenExtractor.extractTokenRequest(request);
    authService.withdraw(userInfo.getUserId(), tokenServiceRequest);
    tokenCookieManager.removeRefreshTokenCookie(response);
    return ApiResponse.OK(null);
  }
}
