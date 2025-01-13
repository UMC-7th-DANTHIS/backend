package com.danthis.backend.api.auth;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<TokenServiceResponse> loginKakao(
      @RequestParam(name = "code") String code,
      HttpServletResponse response) throws JsonProcessingException {
    TokenServiceResponse tokenServiceResponse = authService.kakaoLogin(code);

    tokenCookieManager.addRefreshTokenCookie(response, tokenServiceResponse.getRefreshToken());

    TokenServiceResponse tokenServiceResponseWithoutRefreshToken = tokenServiceResponse.withoutRefreshToken();
    return new ResponseEntity<>(tokenServiceResponseWithoutRefreshToken, HttpStatus.OK);
  }

  @Operation(summary = "토큰 재발급 API")
  @GetMapping("/reissue")
  public ResponseEntity<TokenServiceResponse> reissueToken(
      HttpServletRequest request,
      HttpServletResponse response) {
    TokenServiceRequest tokenServiceRequest = tokenExtractor.extractTokenRequest(request);
    TokenServiceResponse tokenServiceResponse = authService.reissueAccessToken(tokenServiceRequest);

    tokenCookieManager.addRefreshTokenCookie(response, tokenServiceResponse.getRefreshToken());

    TokenServiceResponse tokenServiceResponseWithoutRefreshToken = tokenServiceResponse.withoutRefreshToken();
    return new ResponseEntity<>(tokenServiceResponseWithoutRefreshToken, HttpStatus.OK);
  }

  @Operation(summary = "로그아웃 API")
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(
      HttpServletRequest request,
      HttpServletResponse response) {
    TokenServiceRequest tokenServiceRequest = tokenExtractor.extractTokenRequest(request);
    authService.logout(tokenServiceRequest);

    tokenCookieManager.removeRefreshTokenCookie(response);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "회원 탈퇴 API")
  @AssignCurrentUserInfo
  @DeleteMapping("/withdraw")
  public ResponseEntity<Void> withdraw(
      CurrentUserInfo userInfo,
      HttpServletRequest request,
      HttpServletResponse response) {
    TokenServiceRequest tokenServiceRequest = tokenExtractor.extractTokenRequest(request);
    authService.withdraw(userInfo.getUserId(), tokenServiceRequest);

    tokenCookieManager.removeRefreshTokenCookie(response);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
