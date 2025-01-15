package com.danthis.backend.api.user;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.user.request.UserUpdateRequest;
import com.danthis.backend.application.user.UserService;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "유저 관리", description = "유저 정보 관련 API")
public class UserController {

  private final UserService userService;

  @Operation(summary = "유저 정보 수정 API", description = "유저의 정보를 수정합니다.")
  @PutMapping
  @AssignCurrentUserInfo
  public ApiResponse<Void> updateUser(
      CurrentUserInfo userInfo,
      @RequestBody @Valid UserUpdateRequest request) {
    userService.updateUserInfo(userInfo.getUserId(), request.toServiceRequest());
    return ApiResponse.OK(null);
  }

  //TODO: 유저 정보 조회 API 추가
  //TODO: 닉네임 중복 검사 API 추가
}
