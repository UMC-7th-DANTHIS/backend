package com.danthis.backend.api.user;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.user.request.UserUpdateRequest;
import com.danthis.backend.application.user.UserService;
import com.danthis.backend.application.user.response.UserCommunityResponse.UserCommentsResponse;
import com.danthis.backend.application.user.response.UserCommunityResponse.UserPostsResponse;
import com.danthis.backend.application.user.response.UserInfoResponse;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @Operation(summary = "닉네임 중복 검사 API", description = "사용 가능한 닉네임인지 확인합니다.")
  @GetMapping("/check-nickname")
  public ApiResponse<Boolean> checkNicknameAvailability(
      @RequestParam String nickname) {
    boolean isAvailable = userService.isNicknameAvailable(nickname);
    return ApiResponse.OK(isAvailable);
  }

  @Operation(summary = "유저 정보 조회 API", description = "유저 정보를 조회합니다.")
  @GetMapping("/me")
  @AssignCurrentUserInfo
  public ApiResponse<UserInfoResponse> getUserInfo(CurrentUserInfo userInfo) {
    UserInfoResponse response = userService.getUserInfo(userInfo.getUserId());
    return ApiResponse.OK(response);
  }

  @Operation(summary = "유저의 게시글 조회 API", description = "유저가 쓴 게시글을 조회합니다.")
  @GetMapping("/posts")
  @AssignCurrentUserInfo
  public ApiResponse<UserPostsResponse> getUserPosts(
      CurrentUserInfo userInfo,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "4") Integer size) {
    UserPostsResponse response = userService.getUserPosts(userInfo.getUserId(), page, size);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "유저의 댓글 조회 API", description = "유저가 쓴 댓글을 조회합니다.")
  @GetMapping("/comments")
  @AssignCurrentUserInfo
  public ApiResponse<UserCommentsResponse> getUserComments(
      CurrentUserInfo userInfo,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "6") Integer size) {
    UserCommentsResponse response = userService.getUserComments(userInfo.getUserId(), page, size);
    return ApiResponse.OK(response);
  }
}
