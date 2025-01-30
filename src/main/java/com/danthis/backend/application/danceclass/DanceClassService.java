package com.danthis.backend.application.danceclass;

import com.danthis.backend.application.danceclass.implement.DanceClassManager;
import com.danthis.backend.application.danceclass.implement.DanceClassMapper;
import com.danthis.backend.application.danceclass.implement.DanceClassReader;
import com.danthis.backend.application.danceclass.request.DanceClassCreateServiceRequest;
import com.danthis.backend.application.danceclass.response.DanceClassListServiceResponse;
import com.danthis.backend.application.danceclass.response.DanceClassReadServiceResponse;
import com.danthis.backend.common.exception.BusinessException;
import com.danthis.backend.common.exception.ErrorCode;
import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.classreview.repository.ClassReviewRepository;
import com.danthis.backend.domain.danceclass.DanceClass;
import com.danthis.backend.domain.danceclass.danceclassimage.DanceClassImage;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.hashtag.Hashtag;
import com.danthis.backend.domain.mapping.danceclasshashtag.DanceClassHashtag;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DanceClassService {

  private final DanceClassManager danceClassManager;
  private final DanceClassReader danceClassReader;
  private final DanceClassMapper danceClassMapper;
  private final DancerRepository dancerRepository;
  private final ClassReviewRepository classReviewRepository;

  @Transactional
  public void createDanceClass(DanceClassCreateServiceRequest request) {
    Genre genre = danceClassReader.readGenreById(request.getGenre());
    Set<Hashtag> hashtags = danceClassReader.readHashtagsByIds(request.getHashtags());
    Dancer dancer = dancerRepository.findById(request.getDancerId())
                                    .orElseThrow(
                                        () -> new BusinessException(ErrorCode.DANCER_NOT_FOUND));

    DanceClass danceClass = danceClassMapper.mapToEntity(request, genre, dancer);
    danceClassManager.saveDanceClass(danceClass);

    Set<DanceClassHashtag> hashtagMappings = danceClassMapper.mapToHashtags(danceClass, hashtags);
    danceClassManager.saveDanceClassHashtags(hashtagMappings);

    if (request.getImages() != null) {
      Set<DanceClassImage> images = danceClassMapper.mapToImages(danceClass, request.getImages());
      danceClassManager.saveDanceClassImages(images);
    }
  }

  @Transactional
  public DanceClassReadServiceResponse getDanceClassDetail(Long classId) {
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    return danceClassMapper.toDanceClassDetailsResponse(danceClass);
  }

  @Transactional
  public DanceClassReadServiceResponse getDanceClassReviews(Long classId, Integer page,
      Integer size) {
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);
    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<ClassReview> reviewsPage = classReviewRepository.findByDanceClassId(classId, pageable);

    return danceClassMapper.toDanceClassReviewsResponse(danceClass, reviewsPage);
  }

  @Transactional
  public DanceClassReadServiceResponse getDanceClassAverageRating(Long classId) {
    DanceClass danceClass = danceClassReader.readDanceClassById(classId);

    Double averageRating = classReviewRepository.calculateAverageRatingByDanceClassId(classId);
    long totalReviews = classReviewRepository.countByDanceClassId(classId);

    return danceClassMapper.toDanceClassRatingResponse(danceClass, averageRating, totalReviews);
  }

  @Transactional
  public DanceClassListServiceResponse getDanceClassList(Long genreId, int page, int size) {
    PageRequest pageable = PageRequest.of(page - 1, size);
    Page<DanceClass> danceClasses = danceClassReader.readDanceClasses(genreId, pageable);

    return DanceClassListServiceResponse.from(danceClasses);
  }
}
