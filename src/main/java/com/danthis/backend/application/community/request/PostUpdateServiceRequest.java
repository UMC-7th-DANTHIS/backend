package com.danthis.backend.application.community.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateServiceRequest {

  private Long postId;
  private Long userId;
  private String title;
  private String content;
  private List<String> images;
}
