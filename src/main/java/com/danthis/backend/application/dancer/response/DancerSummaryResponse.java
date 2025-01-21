package com.danthis.backend.application.dancer.response;

import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DancerSummaryResponse {

  private Long id;
  private String dancerName;
  private Set<String> imageUrlList;
}
