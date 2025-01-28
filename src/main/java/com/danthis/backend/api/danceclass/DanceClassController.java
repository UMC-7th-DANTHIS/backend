package com.danthis.backend.api.danceclass;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.danceclass.request.DanceClassCreateRequest;
import com.danthis.backend.application.danceclass.DanceClassService;
import com.danthis.backend.application.danceclass.response.DanceClassReadServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

  @Operation(summary = "댄스 수업 등록 API", description = "새로운 댄스 수업을 등록합니다.")
  @PostMapping("/")
  public ApiResponse<Void> registerDanceClass(@RequestBody @Valid DanceClassCreateRequest request) {
    danceClassService.createDanceClass(request.toServiceRequest());
    return ApiResponse.OK(null);
  }

  @Operation(summary = "댄스 수업 단일 조회 상세 설명 API", description = "댄스 수업 상세 설명 섹션을 조회합니다.")
  @GetMapping("/{classId}")
  public ApiResponse<DanceClassReadServiceResponse> getDanceClassDetails(
      @PathVariable Long classId) {
    DanceClassReadServiceResponse response = danceClassService.getDanceClassDetail(classId);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "댄스 수업 단일 조회 리뷰 목록 API", description = "댄스 수업 리뷰 목록 섹션을 조회합니다.")
  @GetMapping("/{classId}/reviews")
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
  public ApiResponse<DanceClassReadServiceResponse> getDanceClassAverageRating(
      @PathVariable Long classId) {
    DanceClassReadServiceResponse response = danceClassService.getDanceClassAverageRating(classId);
    return ApiResponse.OK(response);
  }
}
