package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.mapping.userdancer.QUserDancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
}
