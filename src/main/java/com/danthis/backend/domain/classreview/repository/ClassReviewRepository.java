package com.danthis.backend.domain.classreview.repository;

import com.danthis.backend.domain.classreview.ClassReview;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassReviewRepository extends JpaRepository<ClassReview, Long>,
    ClassReviewRepositoryCustom {

  Page<ClassReview> findByDanceClassId(Long classId, Pageable pageable);

  Long countByDanceClassId(Long classId);

  Optional<ClassReview> findByIdAndDanceClassId(Long reviewId, Long classId);
}
