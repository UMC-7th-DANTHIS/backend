package com.danthis.backend.domain.communitycomment.repository;

import com.danthis.backend.domain.communitycomment.CommunityComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long>,
    CommunityCommentRepositoryCustom {

  List<CommunityComment> findByPostId(Long postId);
}
