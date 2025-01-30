package com.danthis.backend.domain.mapping.usergenre.repository;

import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import java.util.List;

public interface UserGenreRepositoryCustom {

  void deleteByUser(Long userId);

  List<UserGenre> findByUser(Long userId);
}
