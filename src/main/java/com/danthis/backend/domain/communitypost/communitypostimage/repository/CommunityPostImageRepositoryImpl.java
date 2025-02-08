package com.danthis.backend.domain.communitypost.communitypostimage.repository;

import com.danthis.backend.domain.communitypost.communitypostimage.QCommunityPostImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityPostImageRepositoryImpl implements CommunityPostImageRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QCommunityPostImage qCommunityPostImage = QCommunityPostImage.communityPostImage;

  @Override
  public void deleteByPostId(Long postId) {
    jpaQueryFactory.delete(qCommunityPostImage)
                   .where(qCommunityPostImage.post.id.eq(postId)
                                                     .and(qCommunityPostImage.isActive.eq(true)))
                   .execute();
  }
}
