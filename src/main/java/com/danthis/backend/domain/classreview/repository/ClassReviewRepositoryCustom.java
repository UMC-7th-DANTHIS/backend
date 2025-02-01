package com.danthis.backend.domain.classreview.repository;

import com.danthis.backend.domain.classreview.ClassReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassReviewRepositoryCustom {

  Page<ClassReview> findByUserId(Long userId, Pageable pageable);
}
