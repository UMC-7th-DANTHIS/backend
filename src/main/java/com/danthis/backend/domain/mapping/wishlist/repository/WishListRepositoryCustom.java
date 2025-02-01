package com.danthis.backend.domain.mapping.wishlist.repository;

import com.danthis.backend.domain.mapping.wishlist.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishListRepositoryCustom {

  Page<WishList> findByUserId(Long userId, Pageable pageable);
}
