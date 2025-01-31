package com.danthis.backend.domain.danceclass.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DanceClassRepositoryCustom {

  Page<DanceClass> searchByClassName(String query, Pageable pageable);
}
