package com.danthis.backend.api.community.request;

import com.danthis.backend.application.community.request.PostCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class PostCreateRequest {

  @NotBlank(message = "게시글 제목은 필수 입력값입니다.")
  @Size(max = 50, message = "게시글 제목은 최대 50자까지 가능합니다.")
  private String title;

  @NotBlank(message = "게시글 내용은 필수 입력값입니다.")
  @Size(max = 1000, message = "게시글 내용은 최대 1000자까지 가능합니다.")
  private String content;

  @Size(max = 4, message = "게시글 이미지는 최대 4개까지 등록 가능합니다.")
  private List<String> images;

  public PostCreateServiceRequest toServiceRequest(Long userId) {
    return PostCreateServiceRequest.builder()
                                   .userId(userId)
                                   .title(title)
                                   .content(content)
                                   .images(images)
                                   .build();
  }
}
