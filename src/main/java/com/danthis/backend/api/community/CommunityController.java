package com.danthis.backend.api.community;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.community.request.PostCreateRequest;
import com.danthis.backend.api.community.request.PostUpdateRequest;
import com.danthis.backend.application.community.PostService;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("community")
@RequiredArgsConstructor
@Tag(name = "커뮤니티", description = "커뮤니티 관련 API")
public class CommunityController {

  private final PostService postService;

  @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다.")
  @PostMapping("/posts")
  @AssignCurrentUserInfo
  public ApiResponse<Void> createPost(
      @RequestBody @Valid PostCreateRequest request, CurrentUserInfo userInfo) {
    postService.createPost(request.toServiceRequest(userInfo.getUserId()));
    return ApiResponse.OK(null);
  }

  @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
  @PutMapping("/posts/{postId}")
  @AssignCurrentUserInfo
  public ApiResponse<Void> updatePost(
      @PathVariable Long postId,
      @RequestBody @Valid PostUpdateRequest request,
      CurrentUserInfo userInfo
  ) {
    postService.updatePost(request.toServiceRequest(postId, userInfo.getUserId()));
    return ApiResponse.OK(null);
  }

  @Operation(summary = "게시글 삭제", description = "기존 게시글을 삭제합니다.")
  @DeleteMapping("/posts/{postId}")
  @AssignCurrentUserInfo
  public ApiResponse<Void> deletePost(
      @PathVariable Long postId,
      CurrentUserInfo userInfo
  ) {
    postService.deletePost(postId, userInfo.getUserId());
    return ApiResponse.OK(null);
  }
}
