package com.danthis.backend.application.dancer.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DancerInfoResponse {

  private Long id;
  private String dancerName;
  private String instargramId;
  private String bio;
  private String history;
  private String openChatUrl;
  private List<Long> favoriteGenres;
  private List<Long> imageUrlList;
}
