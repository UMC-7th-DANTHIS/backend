package com.danthis.backend.api.danceclass.request;

import com.danthis.backend.application.danceclass.request.DanceClassCreateServiceRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;

@Getter
public class DanceClassCreateRequest {

  @NotBlank(message = "수업 이름은 필수 입력값입니다.")
  @Size(max = 20, message = "수업 이름은 최대 20자까지 가능합니다.")
  private String className;

  @NotNull(message = "회당 가격은 필수 입력값입니다.")
  private Integer pricePerSession;

  @NotNull(message = "난이도는 필수 입력값입니다.")
  @Min(value = 1)
  @Max(value = 5)
  private Integer difficulty;

  @NotNull(message = "장르는 필수 입력값입니다.")
  private Long genre;

  @NotBlank(message = "수업 소개는 필수 입력값입니다.")
  @Size(max = 1000, message = "수업 소개는 최대 1000자까지 가능합니다")
  private String description;

  @NotBlank(message = "수업 추천 대상은 필수 입력값입니다.")
  @Size(max = 1000, message = "수업 추천 대상은 최대 1000자까지 가능합니다")
  private String targetAudience;

  @Size(max = 3, message = "해시태그는 최대 3개까지 선택 가능합니다.")
  private Set<Long> hashtags;

  private Set<String> images;

  @NotBlank(message = "수업 추천 대상은 필수 입력값입니다.")
  private String videoUrl;

  public DanceClassCreateServiceRequest toServiceRequest() {
    return DanceClassCreateServiceRequest.builder()
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
