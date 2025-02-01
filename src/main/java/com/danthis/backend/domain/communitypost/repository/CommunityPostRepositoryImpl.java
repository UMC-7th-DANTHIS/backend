package com.danthis.backend.domain.communitypost.repository;

import static com.danthis.backend.domain.communitypost.QCommunityPost.communityPost;

import com.danthis.backend.domain.communitypost.CommunityPost;
import com.querydsl.core.types.dsl.BooleanExpression;
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
}
