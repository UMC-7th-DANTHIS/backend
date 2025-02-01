package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.mapping.userdancer.QUserDancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
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
public class UserDancerRepositoryImpl implements UserDancerRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QUserDancer userDancer = QUserDancer.userDancer;

  @Override
  public void deleteByUser(Long userId) {
    jpaQueryFactory.delete(userDancer)
                   .where(userDancer.user.id.eq(userId)
                                            .and(userDancer.isActive.eq(true)))
                   .execute();
  }

  @Override
  public List<UserDancer> findByUser(Long userId) {
    return jpaQueryFactory.selectFrom(userDancer)
                          .where(userDancer.user.id.eq(userId)
                                                   .and(userDancer.isActive.eq(true)))
                          .fetch();
  }

  public Page<UserDancer> findByUserId(Long userId, Pageable pageable) {
    List<UserDancer> userDancers = jpaQueryFactory.selectFrom(userDancer)
                                                  .where(userDancer.user.id.eq(userId)
                                                                           .and(userDancer.isActive.eq(true)))
                                                  .offset(pageable.getOffset())
                                                  .limit(pageable.getPageSize())
                                                  .fetch();

    long totalElements = Optional.ofNullable(jpaQueryFactory
        .select(userDancer.count())
        .from(userDancer)
        .where(userDancer.user.id.eq(userId)
                                 .and(userDancer.isActive.eq(true)))
        .fetchOne()).orElse(0L);

    return new PageImpl<>(userDancers, pageable, totalElements);
  }
}
