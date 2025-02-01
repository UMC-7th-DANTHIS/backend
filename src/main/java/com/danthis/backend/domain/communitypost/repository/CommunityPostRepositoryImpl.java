package com.danthis.backend.domain.communitypost.repository;

import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.communitypost.QCommunityPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
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
  public Page<CommunityPost> searchByPostTitle(String query, Pageable pageable) {
    BooleanExpression searchCondition = communityPost.title.containsIgnoreCase(query)
                                                           .and(communityPost.isActive.eq(true));

    List<CommunityPost> results = jpaQueryFactory.selectFrom(communityPost)
                                                 .where(searchCondition)
                                                 .offset(pageable.getOffset())
                                                 .limit(pageable.getPageSize())
                                                 .fetch();

    long total = jpaQueryFactory.select(communityPost.count())
                                .from(communityPost)
                                .where(searchCondition)
                                .fetchOne();

    return new PageImpl<>(results, pageable, total);
  }

  @Override
  public Page<CommunityPost> findByUserId(Long userId, Pageable pageable) {
    List<CommunityPost> posts = jpaQueryFactory.selectFrom(communityPost)
                                               .where(communityPost.user.id.eq(userId)
                                                                           .and(communityPost.isActive.eq(true)))
                                               .offset(pageable.getOffset())
                                               .limit(pageable.getPageSize())
                                               .fetch();

    long totalElements = Optional.ofNullable(jpaQueryFactory
                                     .select(communityPost.count())
                                     .from(communityPost)
                                     .where(communityPost.user.id.eq(userId)
                                                                 .and(communityPost.isActive.eq(true)))
                                     .fetchOne())
                                 .orElse(0L);

    return new PageImpl<>(posts, pageable, totalElements);
  }
}
