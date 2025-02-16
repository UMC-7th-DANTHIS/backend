package com.danthis.backend.application.community;

import com.danthis.backend.application.community.implement.CommentManager;
import com.danthis.backend.application.community.implement.CommentMapper;
import com.danthis.backend.application.community.implement.CommentReader;
import com.danthis.backend.application.community.implement.PostReader;
import com.danthis.backend.application.community.request.CommentCreateServiceRequest;
import com.danthis.backend.application.community.response.CommentListServiceResponse;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentManager commentManager;
  private final CommentMapper commentMapper;
  private final CommentReader commentReader;
  private final PostReader postReader;
  private final UserReader userReader;

  @Transactional
  public void createComment(CommentCreateServiceRequest request) {
    CommunityPost post = postReader.readPostById(request.getPostId());
    User user = userReader.readUserById(request.getUserId());

    CommunityComment comment = commentMapper.mapToEntity(request, user, post);
    commentManager.saveComment(comment);
  }

  @Transactional
  public CommentListServiceResponse getCommentsByPostId(Long postId, int page, int size) {
    Page<CommunityComment> commentsPage = commentReader.readCommentsByPostId(postId, page, size);

    return commentMapper.toCommentListResponse(postId, commentsPage);
  }

  @Transactional
  public void deleteComment(Long postId, Long commentId, Long userId) {
    CommunityPost post = postReader.readPostById(postId);
    CommunityComment comment = commentReader.readCommentById(commentId);
    User user = userReader.readUserById(userId);

    if (!comment.getUser().equals(user)) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    commentManager.deleteComment(comment);
  }

  @Transactional
  public void deleteCommentsByPostId(Long postId) {
    List<CommunityComment> comments = commentReader.readCommentsByPostId(postId);
    comments.forEach(commentManager::deleteComment);
  }
}
