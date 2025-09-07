package com.danthis.backend.domain.danceclass.repository;

import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassschedule.Week;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DanceClassRepositoryCustom {

  Page<DanceClass> findByGenreIdAndDate(Long genreId, LocalDate date, PageRequest pageable);

  Page<DanceClass> findByGenreIdAndDay(Long genreId, Week day, PageRequest pageable);
}
