package com.danthis.backend.application.danceclass.implement;

import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.repository.DanceClassRepository;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.genre.repository.GenreRepository;
import com.danthis.backend.domain.hashtag.Hashtag;
import com.danthis.backend.domain.hashtag.repository.HashtagRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DanceClassReader {

  private final GenreRepository genreRepository;
  private final HashtagRepository hashtagRepository;
  private final DanceClassRepository danceClassRepository;

  public Genre readGenreById(Long genreId) {
    return genreRepository.findById(genreId)
                          .orElseThrow(() -> new BusinessException(ErrorCode.GENRE_NOT_FOUND));
  }

  public Set<Hashtag> readHashtagsByIds(Set<Long> hashtagIds) {
    return hashtagIds.stream()
                     .map(hashtagRepository::findById)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .collect(Collectors.toSet());
  }

  public DanceClass readDanceClassById(Long classId) {
    return danceClassRepository.findById(classId)
                               .orElseThrow(
                                   () -> new BusinessException(ErrorCode.DANCE_CLASS_NOT_FOUND));
  }

  public Page<DanceClass> readDanceClasses(Long genreId, PageRequest pageable) {
    if (genreId != null) {
      return danceClassRepository.findByGenreId(genreId, pageable);
    }
    return danceClassRepository.findAll(pageable);
  }
}
