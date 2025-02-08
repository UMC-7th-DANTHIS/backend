package com.danthis.backend.api.community.request;

import com.danthis.backend.application.community.request.CommentCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

  @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
  @Size(max = 200, message = "댓글 내용은 최대 200자까지 가능합니다.")
  private String content;

  public CommentCreateServiceRequest toServiceRequest(Long postId, Long userId) {
    return CommentCreateServiceRequest.builder()
                                      .postId(postId)
                                      .userId(userId)
                                      .content(content)
                                      .build();
  }
}
