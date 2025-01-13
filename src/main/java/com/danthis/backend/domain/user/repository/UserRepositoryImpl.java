package com.danthis.backend.domain.user.repository;

import com.danthis.backend.domain.user.QUser;
import com.danthis.backend.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QUser user = QUser.user;

  @Override
  public Optional<User> findByEmail(String email) {
    User result = jpaQueryFactory.selectFrom(user)
                                 .where(user.email.eq(email)
                                                  .and(user.isActive.eq(true)))
                                 .fetchOne();
    return Optional.ofNullable(result);
  }
}
