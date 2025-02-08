package com.danthis.backend.application.community.implement.mapping;

import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.communitypost.communitypostimage.CommunityPostImage;
import com.danthis.backend.domain.communitypost.communitypostimage.repository.CommunityPostImageRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostImageManager {

  private final CommunityPostImageRepository postImageRepository;

  @Transactional
  public void updatePostImages(CommunityPost post, List<String> newImages) {
    postImageRepository.deleteByPostId(post.getId());

    Set<CommunityPostImage> postImages = newImages.stream()
                                                  .map(url -> CommunityPostImage.builder()
                                                                                .url(url)
                                                                                .post(post)
                                                                                .build())
                                                  .collect(Collectors.toSet());

    postImageRepository.saveAll(postImages);
    post.updatePostImages(postImages);
  }

  @Transactional
  public void deletePostImages(Long postId) {
    postImageRepository.deleteByPostId(postId);
  }
}
