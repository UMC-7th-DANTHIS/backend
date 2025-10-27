package com.danthis.backend.domain.practiceroom.repository;

import com.danthis.backend.domain.practiceroom.PracticeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeRoomRepository extends
    JpaRepository<PracticeRoom, Long>, PracticeRoomRepositoryCustom {

}
