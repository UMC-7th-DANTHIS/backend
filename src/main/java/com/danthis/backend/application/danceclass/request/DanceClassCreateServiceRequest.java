package com.danthis.backend.application.danceclass.request;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DanceClassCreateServiceRequest {

  private String className;
  private Integer pricePerSession;
  private Integer difficulty;
  private Long genre;
  private String description;
  private String targetAudience;
  private Set<Long> hashtags;
  private Set<String> images;
  private String videoUrl;
}
