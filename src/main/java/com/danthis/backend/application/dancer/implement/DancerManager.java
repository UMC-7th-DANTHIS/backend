package com.danthis.backend.application.dancer.implement;

import com.danthis.backend.application.dancer.request.DancerAddServiceRequest;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.dancer.repository.DancerRepository;
import com.danthis.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DancerManager {

  private final DancerRepository dancerRepository;

  public void savedDancer(Dancer dancer) {
    dancerRepository.save(dancer);
  }

  public Dancer createDancer(User user, DancerAddServiceRequest request) {
    Boolean permission = true;  // 모든 댄서 가입이 바로 허용된다. 나중에 false로 변경해야 함
    return Dancer.builder()
                 .user(user)
                 .dancerName(request.getDancerName())
                 .instargramId(request.getInstargramId())
                 .bio(request.getBio())
                 .history(request.getHistory())
                 .isApproved(permission)
                 .build();
  }
}
