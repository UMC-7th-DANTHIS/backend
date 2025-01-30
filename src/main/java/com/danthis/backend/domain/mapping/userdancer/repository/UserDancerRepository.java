package com.danthis.backend.domain.mapping.userdancer.repository;

import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDancerRepository extends JpaRepository<UserDancer, Long>,
    UserDancerRepositoryCustom {

}
