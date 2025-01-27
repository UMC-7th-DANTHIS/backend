package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.mapping.userdancer.QUserDancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
  public Page<UserDancer> findByUserId(Long userId, Pageable pageable) {
    List<UserDancer> userDancers = jpaQueryFactory.selectFrom(userDancer)
                                                  .where(userDancer.user.id.eq(userId))
                                                  .offset(pageable.getOffset())
                                                  .limit(pageable.getPageSize())
                                                  .fetch();

    return new PageImpl<>(userDancers, pageable, userDancers.size());
  }
}
