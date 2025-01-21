package com.danthis.backend.application.dancer.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DancerUpdateServiceRequest {

  private Long id;
  private String dancerName;
  private String instargramId;
  private String openChatUrl;
  private String bio;
  private String history;
  private Set<Long> preferredGenres;
  private Set<String> dancerImages;
}
