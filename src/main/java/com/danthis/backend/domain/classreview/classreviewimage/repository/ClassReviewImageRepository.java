package com.danthis.backend.domain.classreview.classreviewimage.repository;

import com.danthis.backend.domain.classreview.classreviewimage.ClassReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassReviewImageRepository extends JpaRepository<ClassReviewImage, Long>,
    ClassReviewImageRepositoryCustom {

}
