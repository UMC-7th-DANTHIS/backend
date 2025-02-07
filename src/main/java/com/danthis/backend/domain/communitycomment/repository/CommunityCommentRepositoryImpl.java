package com.danthis.backend.domain.communitycomment.repository;

import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.communitycomment.QCommunityComment;
import com.danthis.backend.domain.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityCommentRepositoryImpl implements CommunityCommentRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QCommunityComment communityComment = QCommunityComment.communityComment;
  private final QUser user = QUser.user;

  @Override
  public Page<CommunityComment> findByUserId(Long userId, Pageable pageable) {
    List<CommunityComment> comments = jpaQueryFactory.selectFrom(communityComment)
                                                     .where(communityComment.user.id.eq(userId))
                                                     .offset(pageable.getOffset())
                                                     .limit(pageable.getPageSize())
                                                     .fetch();

    return new PageImpl<>(comments, pageable, comments.size());
  }

  @Override
  public Page<CommunityComment> findByPostId(Long postId, Pageable pageable) {
    List<CommunityComment> comments = jpaQueryFactory.selectFrom(communityComment)
                                                     .leftJoin(communityComment.user, user)
                                                     .fetchJoin()
                                                     .where(communityComment.post.id.eq(postId))
                                                     .offset(pageable.getOffset())
                                                     .limit(pageable.getPageSize())
                                                     .fetch();

    long total = jpaQueryFactory.select(communityComment.count())
                                .from(communityComment)
                                .where(communityComment.post.id.eq(postId))
                                .fetchOne();

    return new PageImpl<>(comments, pageable, total);
  }
}
