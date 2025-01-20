package com.danthis.backend.api.dancer;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.dancer.request.DancerAddRequest;
import com.danthis.backend.api.dancer.request.DancerUpdateRequest;
import com.danthis.backend.application.dancer.DancerService;
import com.danthis.backend.application.dancer.response.DancerInfoResponse;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dancers")
@RequiredArgsConstructor
@Tag(name = "댄서 관리", description = "댄서 정보 관련 API")
public class DancerController {

  private final DancerService dancerService;

  @Operation(summary = "댄서 정보 등록 API", description = "댄서의 정보를 새로 등록합니다.")
  @PostMapping
  @AssignCurrentUserInfo
  public ApiResponse<Long> addDancer(
      CurrentUserInfo userInfo,
      @RequestBody @Valid DancerAddRequest request) {
    Long dancerId = dancerService.addDancerInfo(userInfo.getUserId(), request.toServiceRequest());
    return ApiResponse.OK(dancerId);
  }

  @Operation(summary = "댄서 정보 수정 API", description = "댄서의 정보를 수정합니다.")
  @PutMapping
  public ApiResponse<Long> updateDancer(
      @RequestBody @Valid DancerUpdateRequest request) {
    Long dancerId = dancerService.updateDancerInfo(request.toServiceRequest());
    return ApiResponse.OK(dancerId);
  }

  @Operation(summary = "댄서 정보 조회 API", description = "댄서의 정보를 조회합니다.")
  @GetMapping("/{dancerId}")
  public ApiResponse<DancerInfoResponse> getDancerInfo(
      @PathVariable("dancerId") Long dancerId) {
    DancerInfoResponse response = dancerService.getDancerInfo(dancerId);
    return ApiResponse.OK(response);
  }
}
