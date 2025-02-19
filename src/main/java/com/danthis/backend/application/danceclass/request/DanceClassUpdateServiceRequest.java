package com.danthis.backend.application.danceclass.request;

import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DanceClassUpdateServiceRequest {

  private Long classId;
  private Long userId;
  private String className;
  private Integer pricePerSession;
  private Integer difficulty;
  private Long genre;
  private String description;
  private String targetAudience;
  private Set<Long> hashtags;
  private List<String> images;
  private String videoUrl;
}
