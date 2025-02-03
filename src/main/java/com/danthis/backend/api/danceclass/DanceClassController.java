package com.danthis.backend.api.danceclass;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.danceclass.request.DanceClassBookingApproveRequest;
import com.danthis.backend.api.danceclass.request.DanceClassCreateRequest;
import com.danthis.backend.application.danceclass.DanceClassService;
import com.danthis.backend.application.danceclass.response.DanceClassBookingServiceResponse;
import com.danthis.backend.application.danceclass.response.DanceClassListServiceResponse;
import com.danthis.backend.application.danceclass.response.DanceClassReadServiceResponse;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dance-classes")
@RequiredArgsConstructor
@Tag(name = "댄스 수업", description = "댄스 수업 관련 API")
public class DanceClassController {

  private final DanceClassService danceClassService;

  @Operation(summary = "댄스 수업 등록 API", description = "새로운 댄스 수업을 등록합니다. 댄서만 등록할 수 있습니다.")
  @PostMapping()
  @AssignCurrentUserInfo
  public ApiResponse<Void> registerDanceClass(
      @RequestBody @Valid DanceClassCreateRequest request,
      CurrentUserInfo userInfo) {

    danceClassService.createDanceClass(request.toServiceRequest(), userInfo.getUserId());
    return ApiResponse.OK(null);
  }

  @Operation(summary = "댄스 수업 단일 조회 상세 설명 API", description = "댄스 수업 상세 설명 섹션을 조회합니다.")
  @GetMapping("/{classId}")
  @AssignCurrentUserInfo
  public ApiResponse<DanceClassReadServiceResponse> getDanceClassDetails(
      @PathVariable Long classId) {
    DanceClassReadServiceResponse response = danceClassService.getDanceClassDetail(classId);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "댄스 수업 단일 조회 리뷰 목록 API", description = "댄스 수업 리뷰 목록 섹션을 조회합니다.")
  @GetMapping("/{classId}/reviews")
  @AssignCurrentUserInfo
  public ApiResponse<DanceClassReadServiceResponse> getDanceClassReviews(
      @PathVariable Long classId,
      @RequestParam(defaultValue = "1") @Min(1) Integer page,
      @RequestParam(defaultValue = "5") @Min(1) Integer size
  ) {
    DanceClassReadServiceResponse response = danceClassService.getDanceClassReviews(classId, page,
        size);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "댄스 수업 평균 별점 조회 API", description = "댄스 수업의 전체 평균 별점을 조회합니다.")
  @GetMapping("{classId}/rating")
  @AssignCurrentUserInfo
  public ApiResponse<DanceClassReadServiceResponse> getDanceClassAverageRating(
      @PathVariable Long classId) {
    DanceClassReadServiceResponse response = danceClassService.getDanceClassAverageRating(classId);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "댄스 수업 목록 조회 API", description = "장르를 필터링하여 댄스 수업 목록을 조회합니다.")
  @GetMapping()
  @AssignCurrentUserInfo
  public ApiResponse<DanceClassListServiceResponse> getDanceClasses(
      @RequestParam(required = false) Long genre,
      @RequestParam(defaultValue = "1") @Min(1) int page,
      @RequestParam(defaultValue = "9") @Min(1) int size) {

    DanceClassListServiceResponse response = danceClassService.getDanceClassList(genre, page, size);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "댄스 수업 예약 승인 API", description = "댄서가 유저의 예약을 승인합니다.")
  @PatchMapping("/{classId}/bookings/{userId}/approve")
  @AssignCurrentUserInfo
  public ApiResponse<Void> approveBooking(
      @PathVariable Long classId,
      @PathVariable Long userId,
      @Valid @RequestBody DanceClassBookingApproveRequest request,
      CurrentUserInfo userInfo
  ) {
    danceClassService.approveBooking(userInfo.getUserId(), classId, userId, request);
    return ApiResponse.OK(null);
  }

  @Operation(summary = "댄스 수업 예약 승인된 유저 목록 조회 API", description = "해당 댄스 수업에 예약이 승인된 유저 목록을 조회합니다.")
  @GetMapping("/{classId}/bookings")
  @AssignCurrentUserInfo
  public ApiResponse<DanceClassBookingServiceResponse> getApprovedBookings(
      @PathVariable Long classId) {
    DanceClassBookingServiceResponse response = danceClassService.getApprovedBookings(classId);
    return ApiResponse.OK(response);
  }
}
