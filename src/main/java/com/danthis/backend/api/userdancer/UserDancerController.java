package com.danthis.backend.api.userdancer;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.application.userdancer.UserDancerService;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dancers/{dancerId}/favorite")
@RequiredArgsConstructor
@Tag(name = "댄서 찜 관리", description = "댄서 찜 등록, 해제 관련 API")
public class UserDancerController {

  private final UserDancerService userDancerService;

  @Operation(summary = "댄서 찜 등록 API", description = "댄서를 찜 리스트에 등록합니다.")
  @PostMapping
  @AssignCurrentUserInfo
  public ApiResponse<Long> addFavoriteDancer(
      CurrentUserInfo userInfo,
      @PathVariable("dancerId") Long dancerId) {
    userDancerService.addFavoriteDancer(userInfo.getUserId(), dancerId);
    return ApiResponse.OK(null);
  }

  @Operation(summary = "댄서 찜 해제 API", description = "댄서를 찜 리스트에서 해제합니다.")
  @DeleteMapping
  @AssignCurrentUserInfo
  public ApiResponse<Void> removeFavoriteDancer(
      CurrentUserInfo userInfo,
      @PathVariable("dancerId") Long dancerId) {
    userDancerService.removeFavoriteDancer(userInfo.getUserId(), dancerId);
    return ApiResponse.OK(null);
  }
}
