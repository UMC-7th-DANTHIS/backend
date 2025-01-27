package com.danthis.backend.application.danceclass.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DanceClassReadServiceResponse {

  private Long id;
  private String className;
  private Dancer dancer;
  private Long genre;
  private Integer pricePerSession;
  private Integer difficulty;
  private Details details;

  @Getter
  @Builder
  public static class Dancer {

    private String name;
    private String profileImage;
    private String openChatUrl;
  }

  @Getter
  @Builder
  public static class Details {

    private String videoUrl;
    private String description;
    private String targetAudience;
    private List<Long> hashtags;
    private List<String> danceClassImages;
  }
}
