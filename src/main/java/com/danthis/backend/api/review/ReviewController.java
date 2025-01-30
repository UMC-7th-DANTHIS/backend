package com.danthis.backend.api.review;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.review.request.ReviewCreateRequest;
import com.danthis.backend.application.review.ReviewService;
import com.danthis.backend.application.review.response.ReviewReadServiceResponse;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰 관리", description = "리뷰 관련 API")
public class ReviewController {

  private final ReviewService reviewService;

  @Operation(summary = "리뷰 작성 API", description = "댄스 클래스에 대한 리뷰를 작성합니다.")
  @PostMapping("dance-classes/{classId}/reviews")
  @AssignCurrentUserInfo
  public ApiResponse<Void> createReview(
      CurrentUserInfo userInfo,
      @PathVariable Long classId,
      @RequestBody @Valid ReviewCreateRequest request
  ) {

    reviewService.createReview(userInfo.getUserId(), classId, request.toServiceRequest());
    return ApiResponse.OK(null);
  }

  @Operation(summary = "리뷰 단일 조회 API", description = "특정 댄스 수업의 리뷰를 단일 조회합니다.")
  @GetMapping("dance-classes/{classId}/reviews/{reviewId}")
  @AssignCurrentUserInfo
  public ApiResponse<ReviewReadServiceResponse> getReview(
      @PathVariable Long classId,
      @PathVariable Long reviewId) {
    ReviewReadServiceResponse response = reviewService.getReview(classId, reviewId);
    return ApiResponse.OK(response);
  }
}
