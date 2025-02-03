package com.danthis.backend.domain.dancer.repository;

import com.danthis.backend.domain.dancer.Dancer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DancerRepository extends JpaRepository<Dancer, Long>, DancerRepositoryCustom {

  Optional<Dancer> findByUserId(Long userId);
}
