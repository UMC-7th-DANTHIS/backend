package com.danthis.backend.application.post.implement;

import com.danthis.backend.application.user.response.UserPostsResponse;
import com.danthis.backend.application.user.response.UserPostsResponse.PaginationDto;
import com.danthis.backend.application.user.response.UserPostsResponse.PostDto;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.communitypost.communitypostimage.CommunityPostImage;
import com.danthis.backend.domain.communitypost.repository.CommunityPostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostManager {

  private final CommunityPostRepository communityPostRepository;

  public List<PostDto> toPostDtoList(List<CommunityPost> communityPosts) {
    return communityPosts.stream()
                         .map(communityPost -> PostDto.builder()
                                                      .title(communityPost.getTitle())
                                                      .content(communityPost.getContent())
                                                      .images(communityPost.getCommunityPostImages()
                                                                           .stream()
                                                                           .map(
                                                                               CommunityPostImage::getUrl)
                                                                           .toList())
                                                      .build())
                         .toList();
  }

  public PaginationDto createPagination(Integer currentPage, Integer totalPages) {
    return PaginationDto.builder()
                        .currentPage(currentPage)
                        .totalPages(totalPages)
                        .build();
  }

  public UserPostsResponse createUserPostsResponse(List<PostDto> postDtoList,
      PaginationDto pagination) {
    return UserPostsResponse.builder()
                            .posts(postDtoList)
                            .pagination(pagination)
                            .build();
  }
}
