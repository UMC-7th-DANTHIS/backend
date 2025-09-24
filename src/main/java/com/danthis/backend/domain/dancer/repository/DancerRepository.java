package com.danthis.backend.domain.dancer.repository;

import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DancerRepository extends JpaRepository<Dancer, Long>, DancerRepositoryCustom {

  Optional<Dancer> findByUserId(Long userId);

  boolean existsByUser(User user);

  @Query(
      value = "SELECT DISTINCT * FROM dancer ORDER BY RAND() LIMIT :size",
      nativeQuery = true)
  List<Dancer> getRandomDancers(Integer size);
}
