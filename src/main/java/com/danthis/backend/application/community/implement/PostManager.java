package com.danthis.backend.application.community.implement;

import com.danthis.backend.application.user.response.UserPostsResponse.PostDto;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.communitypost.communitypostimage.CommunityPostImage;
import com.danthis.backend.domain.communitypost.communitypostimage.repository.CommunityPostImageRepository;
import com.danthis.backend.domain.communitypost.repository.CommunityPostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostManager {

  private final CommunityPostRepository communityPostRepository;
  private final CommunityPostImageRepository communityPostImageRepository;

  public List<PostDto> toPostDtoList(List<CommunityPost> communityPosts) {
    return communityPosts.stream()
                         .map(communityPost -> PostDto.builder()
                                                      .postId(communityPost.getId())
                                                      .title(communityPost.getTitle())
                                                      .content(communityPost.getContent())
                                                      .images(communityPost.getCommunityPostImages()
                                                                           .stream()
                                                                           .map(CommunityPostImage::getUrl)
                                                                           .toList())
                                                      .build())
                         .toList();
  }

  public void savePost(CommunityPost post) {
    communityPostRepository.save(post);
  }

  public void savePost(CommunityPost post, List<String> images) {
    CommunityPost savedPost = communityPostRepository.save(post);

    if (images != null && !images.isEmpty()) {
      List<CommunityPostImage> postImages = images.stream()
                                                  .map(url -> CommunityPostImage.builder()
                                                                                .url(url)
                                                                                .post(savedPost)
                                                                                .build())
                                                  .toList();

      communityPostImageRepository.saveAll(postImages);
    }
  }

  public void deletePost(CommunityPost post) {
    communityPostRepository.delete(post);
  }
}
