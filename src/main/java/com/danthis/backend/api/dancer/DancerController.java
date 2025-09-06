package com.danthis.backend.api.dancer;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.dancer.request.DancerAddRequest;
import com.danthis.backend.api.dancer.request.DancerUpdateRequest;
import com.danthis.backend.application.danceclass.DanceClassService;
import com.danthis.backend.application.danceclass.response.DanceClassListServiceResponse;
import com.danthis.backend.application.dancer.DancerService;
import com.danthis.backend.application.dancer.response.DancerInfoResponse;
import com.danthis.backend.application.dancer.response.DancerSummaryListResponse;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.AssignOrNullCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dancers")
@RequiredArgsConstructor
@Tag(name = "댄서 관리", description = "댄서 정보 관련 API")
public class DancerController {

  private final DancerService dancerService;
  private final DanceClassService danceClassService;

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
  @AssignCurrentUserInfo
  public ApiResponse<Long> updateDancer(
      CurrentUserInfo userInfo,
      @RequestBody @Valid DancerUpdateRequest request) {

    Long dancerId = dancerService.updateDancerInfo(userInfo.getUserId(), request.toServiceRequest());
    return ApiResponse.OK(dancerId);
  }

  @Operation(summary = "자신의 댄서 정보 조회 API", description = "자기 자신의 댄서 정보를 조회합니다.")
  @GetMapping()
  @AssignCurrentUserInfo
  public ApiResponse<DancerInfoResponse> getMyDancerInfo(
      CurrentUserInfo userInfo) {

    DancerInfoResponse response = dancerService.getMyDancerInfo(userInfo.getUserId());
    return ApiResponse.OK(response);
  }

  @Operation(summary = "단일 댄서 정보 조회 API", description = "댄서의 정보를 조회합니다.")
  @GetMapping("/info/{dancerId}")
  @AssignOrNullCurrentUserInfo
  public ApiResponse<DancerInfoResponse> getDancerInfo(
      CurrentUserInfo userInfo,
      @PathVariable("dancerId") Long dancerId) {

    DancerInfoResponse response = dancerService.getDancerInfo(userInfo.getUserId(), dancerId);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "장르별 댄서 정보 조회 API", description = "장르별 댄서들의 정보를 조회합니다.")
  @GetMapping("/genres/{genreId}")
  @AssignOrNullCurrentUserInfo
  public ApiResponse<DancerSummaryListResponse> getDancersByGenre(
      @PathVariable("genreId") Long genreId,
      @RequestParam(defaultValue = "1") @Min(1) Integer page,
      @RequestParam(defaultValue = "6") @Min(1) Integer size) {

    DancerSummaryListResponse response = dancerService.getDancersByGenre(genreId, page, size);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "댄서가 생성한 댄스수업 목록 조회 API", description = "댄서가 생성한 댄스수업 목록을 조회합니다.")
  @GetMapping("/dance-classes")
  @AssignCurrentUserInfo
  public ApiResponse<DanceClassListServiceResponse> getDancerClasses(
      CurrentUserInfo userInfo,
      @RequestParam(required = false) @Min(1) Long dancerId,
      @RequestParam(defaultValue = "1") @Min(1) Integer page,
      @RequestParam(defaultValue = "9") @Min(1) Integer size) {

    DanceClassListServiceResponse response = danceClassService.getDancerClasses(userInfo.getUserId(), dancerId, page, size);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "모든 댄서 정보 조회 API", description = "모든 댄서의 정보를 조회합니다.")
  @GetMapping("/all")
  public ApiResponse<DancerSummaryListResponse> getAllDancers() {

    DancerSummaryListResponse response = dancerService.getAllDancers();
    return ApiResponse.OK(response);
  }

  @Operation(summary = "사용자 장르 맞춤 댄서 추천 API", description = "사용자의 선호 장르를 기반으로 해당 장르를 주장르로 하는 댄서를 조회합니다.")
  @GetMapping("/recommendations")
  @AssignCurrentUserInfo
  public ApiResponse<DancerSummaryListResponse> getRecommendationDancers(
      CurrentUserInfo userInfo) {

    DancerSummaryListResponse response = dancerService.getRecommendationDancers(userInfo.getUserId());
    return ApiResponse.OK(response);
  }
}
