package com.danthis.backend.application.community.implement;

import com.danthis.backend.application.community.request.CommentCreateServiceRequest;
import com.danthis.backend.application.community.response.CommentListServiceResponse;
import com.danthis.backend.application.community.response.CommentListServiceResponse.CommentSummary;
import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

  public CommunityComment mapToEntity(CommentCreateServiceRequest request, User user,
      CommunityPost post) {
    return CommunityComment.builder()
                           .content(request.getContent())
                           .user(user)
                           .post(post)
                           .build();
  }

  public CommentListServiceResponse toCommentListResponse(Long postId,
      Page<CommunityComment> commentsPage) {
    List<CommentSummary> comments = commentsPage.getContent().stream()
                                                .map(
                                                    comment -> CommentListServiceResponse.CommentSummary.builder()
                                                                                                        .commentId(comment.getId())
                                                                                                        .userName(comment.getUser().getNickname())
                                                                                                        .userProfileImage(comment.getUser().getProfileImage())
                                                                                                        .createdAt(comment.getCreatedAt())
                                                                                                        .content(comment.getContent())
                                                                                                        .build())
                                                .toList();

    return CommentListServiceResponse.builder()
                                     .postId(postId)
                                     .currentPage(commentsPage.getNumber() + 1)
                                     .totalPages(commentsPage.getTotalPages())
                                     .totalComments((int) commentsPage.getTotalElements())
                                     .comments(comments)
                                     .build();
  }
}
