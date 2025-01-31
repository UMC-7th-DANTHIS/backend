package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.mapping.userdancer.QUserDancer;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDancerRepositoryImpl implements UserDancerRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QUserDancer userDancer = QUserDancer.userDancer;

  @Override
  public UserDancer findUserDancerByUserAndDancer(User user, Dancer dancer) {
    return jpaQueryFactory.selectFrom(userDancer)
                          .where(userDancer.user.eq(user)
                                                .and(userDancer.dancer.eq(dancer)
                                                                      .and(userDancer.isActive.eq(
                                                                          true))))
                          .fetchFirst();
  }
}
