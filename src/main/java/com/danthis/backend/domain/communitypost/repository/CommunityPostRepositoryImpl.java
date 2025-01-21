package com.danthis.backend.domain.communitypost.repository;

import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.communitypost.QCommunityPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityPostRepositoryImpl implements CommunityPostRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QCommunityPost communityPost = QCommunityPost.communityPost;

  @Override
  public Page<CommunityPost> findByUserId(Long userId, Pageable pageable) {
    List<CommunityPost> posts = jpaQueryFactory.selectFrom(communityPost)
                                               .where(communityPost.user.id.eq(userId))
                                               .offset(pageable.getOffset())
                                               .limit(pageable.getPageSize())
                                               .fetch();

    return new PageImpl<>(posts, pageable, posts.size());
  }
}
