package com.danthis.backend.domain.communitypost.repository;

import com.danthis.backend.domain.communitypost.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long>,
    CommunityPostRepositoryCustom {

  Page<CommunityPost> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
