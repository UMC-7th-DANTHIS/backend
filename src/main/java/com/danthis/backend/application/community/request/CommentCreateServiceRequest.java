package com.danthis.backend.application.community.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateServiceRequest {

  private Long postId;
  private Long userId;
  private String content;
}
