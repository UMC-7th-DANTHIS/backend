package com.danthis.backend.api.danceclass.request;

import com.danthis.backend.application.danceclass.request.DanceClassUpdateServiceRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import lombok.Getter;

@Getter
public class DanceClassUpdateRequest {

  @Size(max = 20, message = "수업 이름은 최대 20자까지 가능합니다.")
  private String className;

  private Integer pricePerSession;

  @Min(value = 1)
  @Max(value = 5)
  private Integer difficulty;

  private Long genre;

  @Size(max = 1000, message = "수업 소개는 최대 1000자까지 가능합니다")
  private String description;

  @Size(max = 1000, message = "수업 추천 대상은 최대 1000자까지 가능합니다")
  private String targetAudience;

  @Size(max = 3, message = "해시태그는 최대 3개까지 선택 가능합니다.")
  private Set<Long> hashtags;

  private List<String> images;

  private String videoUrl;

  public DanceClassUpdateServiceRequest toServiceRequest(Long classId, Long userId) {
    return DanceClassUpdateServiceRequest.builder()
                                         .classId(classId)
                                         .userId(userId)
                                         .className(className)
                                         .pricePerSession(pricePerSession)
                                         .difficulty(difficulty)
                                         .genre(genre)
                                         .description(description)
                                         .targetAudience(targetAudience)
                                         .hashtags(hashtags)
                                         .images(images)
                                         .videoUrl(videoUrl)
                                         .build();
  }
}
