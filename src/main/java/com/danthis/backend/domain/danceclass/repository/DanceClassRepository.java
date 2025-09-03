package com.danthis.backend.domain.danceclass.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Long>,
    DanceClassRepositoryCustom {

  Page<DanceClass> findByGenreId(Long genreId, Pageable pageable);

  Page<DanceClass> findByDancerId(Long dancerId, Pageable pageable);

  @Query("""
          SELECT DISTINCT dc FROM DanceClass dc 
          LEFT JOIN dc.danceClassHashtags dh 
          WHERE (:query IS NULL OR LOWER(dc.className) LIKE LOWER(CONCAT('%', :query, '%')))
          AND (:hashtagId IS NULL OR dh.hashtag.id = :hashtagId)
          ORDER BY dc.id ASC
      """)
  Page<DanceClass> findByClassNameAndHashtag(String query, Long hashtagId, Pageable pageable);

  @Query(
      value = "SELECT DISTINCT dc FROM DanceClass dc ORDER BY rand() LIMIT :size",
      nativeQuery = true)
  Page<DanceClass> getRandomDanceClasses(Integer size);
}
