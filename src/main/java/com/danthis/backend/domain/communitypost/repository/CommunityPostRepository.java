package com.danthis.backend.domain.communitypost.repository;

import com.danthis.backend.domain.communitypost.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
}
