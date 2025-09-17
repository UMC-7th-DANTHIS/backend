package com.danthis.backend.api.community;

import com.danthis.backend.api.ApiResponse;
import com.danthis.backend.api.community.request.CommentCreateRequest;
import com.danthis.backend.api.community.request.PostCreateRequest;
import com.danthis.backend.api.community.request.PostUpdateRequest;
import com.danthis.backend.application.community.CommentService;
import com.danthis.backend.application.community.PostService;
import com.danthis.backend.application.community.response.CommentListServiceResponse;
import com.danthis.backend.application.community.response.PostListServiceResponse;
import com.danthis.backend.application.community.response.PostReadServiceResponse;
import com.danthis.backend.common.security.aop.AssignCurrentUserInfo;
import com.danthis.backend.common.security.aop.CurrentUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("community")
@RequiredArgsConstructor
@Tag(name = "커뮤니티", description = "커뮤니티 관련 API")
public class CommunityController {

  private final PostService postService;
  private final CommentService commentService;

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

  @Operation(summary = "게시글 단일 조회", description = "게시글을 단일 조회합니다.")
  @GetMapping("/info/posts/{postId}")
  public ApiResponse<PostReadServiceResponse> getPost(@PathVariable Long postId) {

    PostReadServiceResponse response = postService.getPostById(postId);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다.")
  @GetMapping("/info/posts")
  public ApiResponse<PostListServiceResponse> getPosts(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return ApiResponse.OK(postService.getPosts(page, size));
  }

  @Operation(summary = "댓글 작성", description = "게시글에 댓글을 작성합니다.")
  @PostMapping("/posts/{postId}/comments")
  @AssignCurrentUserInfo
  public ApiResponse<Void> createComment(
      @PathVariable Long postId,
      @RequestBody @Valid CommentCreateRequest request,
      CurrentUserInfo userInfo
  ) {
    commentService.createComment(request.toServiceRequest(postId, userInfo.getUserId()));
    return ApiResponse.OK(null);
  }

  @Operation(summary = "댓글 목록 조회", description = "게시글의 댓글을 조회합니다.")
  @GetMapping("/info/posts/{postId}/comments")
  public ApiResponse<CommentListServiceResponse> getComments(
      @PathVariable Long postId,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size
  ) {
    CommentListServiceResponse response = commentService.getCommentsByPostId(postId, page, size);
    return ApiResponse.OK(response);
  }

  @Operation(summary = "댓글 삭제", description = "기존 댓글을 삭제합니다.")
  @DeleteMapping("/posts/{postId}/comments/{commentId}")
  @AssignCurrentUserInfo
  public ApiResponse<Void> deleteComment(
      @PathVariable Long postId,
      @PathVariable Long commentId,
      CurrentUserInfo userInfo
  ) {
    commentService.deleteComment(postId, commentId, userInfo.getUserId());
    return ApiResponse.OK(null);
  }
}
