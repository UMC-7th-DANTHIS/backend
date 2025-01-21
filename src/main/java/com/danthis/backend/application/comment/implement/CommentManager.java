package com.danthis.backend.application.comment.implement;

import com.danthis.backend.application.user.response.UserCommentsResponse;
import com.danthis.backend.application.user.response.UserCommentsResponse.CommentDto;
import com.danthis.backend.application.user.response.UserCommentsResponse.PaginationDto;
import com.danthis.backend.domain.communitycomment.CommunityComment;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CommentManager {

  public List<CommentDto> toCommentDtoList(List<CommunityComment> communityComments) {
    return communityComments.stream()
                            .map(communityComment -> CommentDto.builder()
                                                               .postId(communityComment.getPost()
                                                                                       .getId())
                                                               .content(
                                                                   communityComment.getContent())
                                                               .build())
                            .toList();
  }

  public PaginationDto createPagination(Integer currentPage, Integer totalPages) {
    return PaginationDto.builder()
                        .currentPage(currentPage)
                        .totalPages(totalPages)
                        .build();
  }

  public UserCommentsResponse createUserCommentsResponse(List<CommentDto> commentDtoList,
      PaginationDto pagination) {
    return UserCommentsResponse.builder()
                               .comments(commentDtoList)
                               .pagination(pagination)
                               .build();
  }
}
