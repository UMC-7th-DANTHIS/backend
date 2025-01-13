package com.danthis.backend.domain.communitypostimage.repository;

import com.danthis.backend.domain.communitypostimage.CommunityPostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostImageRepository extends JpaRepository<CommunityPostImage, Long> {
}
