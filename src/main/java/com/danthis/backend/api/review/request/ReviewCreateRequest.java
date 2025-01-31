package com.danthis.backend.api.review.request;

import com.danthis.backend.application.review.request.ReviewCreateServiceRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;

@Getter
public class ReviewCreateRequest {

  @NotNull(message = "리뷰 제목은 필수 입력값입니다.")
  @Size(max = 50, message = "리뷰 제목은 최대 50자까지 가능합니다.")
  private String title;

  @NotNull(message = "리뷰 내용은 필수 입력값입니다.")
  @Size(max = 1000, message = "리뷰 내용은 최대 1000자까지 가능합니다.")
  private String content;

  @NotNull(message = "평점은 필수 입력값입니다.")
  @Min(value = 1, message = "최소 평점은 1점입니다.")
  @Max(value = 5, message = "최대 평점은 5점입니다.")
  private Integer rating;

  @Size(max = 4, message = "이미지는 최대 4장까지 등록할 수 있습니다.")
  private Set<String> images;

  public ReviewCreateServiceRequest toServiceRequest() {
    return ReviewCreateServiceRequest.builder()
                                     .title(title)
                                     .content(content)
                                     .rating(rating)
                                     .reviewImages(images)
                                     .build();
  }
}
