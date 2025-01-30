package com.danthis.backend.application.dancer.request;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DancerAddServiceRequest {

  private String dancerName;
  private String instargramId;
  private String openChatUrl;
  private String bio;
  private String history;
  private Set<Long> preferredGenres;
  private Set<String> dancerImages;
}
