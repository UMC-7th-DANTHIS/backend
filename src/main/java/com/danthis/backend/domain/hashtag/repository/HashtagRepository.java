package com.danthis.backend.domain.hashtag.repository;

import com.danthis.backend.domain.hashtag.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

}
