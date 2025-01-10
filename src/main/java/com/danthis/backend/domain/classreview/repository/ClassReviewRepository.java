package com.danthis.backend.domain.classreview.repository;

import com.danthis.backend.domain.classreview.ClassReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassReviewRepository extends JpaRepository<ClassReview, Long>,
    ClassReviewRepositoryCustom {

}
