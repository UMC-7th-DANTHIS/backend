package com.danthis.backend.domain.wishlist.repository;

import com.danthis.backend.domain.wishlist.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long>,
    WishListRepositoryCustom {

}
