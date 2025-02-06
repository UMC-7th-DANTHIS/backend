package com.danthis.backend.application.community;

import com.danthis.backend.application.community.implement.PostManager;
import com.danthis.backend.application.community.implement.PostMapper;
import com.danthis.backend.application.community.implement.PostReader;
import com.danthis.backend.application.community.implement.mapping.PostImageManager;
import com.danthis.backend.application.community.request.PostCreateServiceRequest;
import com.danthis.backend.application.community.request.PostUpdateServiceRequest;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

  private final PostManager postManager;
  private final PostMapper postMapper;
  private final PostReader postReader;
  private final PostImageManager postImageManager;
  private final UserReader userReader;

  @Transactional
  public void createPost(PostCreateServiceRequest request) {
    User user = userReader.readUserById(request.getUserId());

    CommunityPost post = postMapper.mapToEntity(request, user);
    postManager.savePost(post, request.getImages());
  }

  @Transactional
  public void updatePost(PostUpdateServiceRequest request) {
    CommunityPost post = postReader.readPostById(request.getPostId());
    User user = userReader.readUserById(request.getUserId());

    if (!post.getUser().equals(user)) {
      throw new BusinessException(ErrorCode.ACCESS_DENIED);
    }

    post.updateTitle(request.getTitle());
    post.updateContent(request.getContent());

    postImageManager.updatePostImages(post, request.getImages());

    postManager.savePost(post);
  }
}
