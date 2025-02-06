package com.danthis.backend.application.community;

import com.danthis.backend.application.community.implement.PostManager;
import com.danthis.backend.application.community.implement.PostMapper;
import com.danthis.backend.application.community.request.PostCreateServiceRequest;
import com.danthis.backend.application.user.implement.UserReader;
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

  private final PostManager communityPostManager;
  private final PostMapper communityPostMapper;
  private final UserReader userReader;

  @Transactional
  public void createPost(PostCreateServiceRequest request) {
    User user = userReader.readUserById(request.getUserId());

    CommunityPost post = communityPostMapper.mapToEntity(request, user);
    communityPostManager.savePost(post, request.getImages());
  }
}
