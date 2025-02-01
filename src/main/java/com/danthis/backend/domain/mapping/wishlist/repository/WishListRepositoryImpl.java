package com.danthis.backend.domain.mapping.wishlist.repository;

import com.danthis.backend.domain.mapping.wishlist.QWishList;
import com.danthis.backend.domain.mapping.wishlist.WishList;
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
public class WishListRepositoryImpl implements WishListRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;
  private final QWishList wishList = QWishList.wishList;

  @Override
  public Page<WishList> findByUserId(Long userId, Pageable pageable) {
    List<WishList> wishLists = jpaQueryFactory.selectFrom(wishList)
                                              .where(wishList.user.id.eq(userId)
                                                                     .and(wishList.isActive.eq(
                                                                         true)))
                                              .offset(pageable.getOffset())
                                              .limit(pageable.getPageSize())
                                              .fetch();

    long totalElements = Optional.ofNullable(jpaQueryFactory
        .select(wishList.count())
        .from(wishList)
        .where(wishList.user.id.eq(userId)
                               .and(wishList.isActive.eq(true)))
        .fetchOne()).orElse(0L);

    return new PageImpl<>(wishLists, pageable, totalElements);
  }
}
