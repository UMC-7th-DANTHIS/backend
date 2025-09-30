package com.danthis.backend.application.community.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReadServiceResponse {

  private Long postId;
  private Long userId;
  private String title;
  private String author;
  private LocalDateTime createdAt;
  private String content;
  private int commentCount;
  private List<String> images;
}
