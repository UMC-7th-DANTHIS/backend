package com.danthis.backend.domain.mapping.wishlist.repository;

import com.danthis.backend.domain.mapping.wishlist.WishList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishListRepositoryCustom {

  Page<WishList> findByUserId(Long userId, Pageable pageable);

  WishList findWishListByUserIdAndClassId(Long userId, Long classId);

  List<WishList> findAllByUserId(Long userId);
}
