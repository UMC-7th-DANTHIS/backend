package com.danthis.backend.application.user;

import com.danthis.backend.application.dancer.implement.DancerReader;
import com.danthis.backend.application.user.implement.UserManager;
import com.danthis.backend.application.user.implement.UserPreferenceMapper;
import com.danthis.backend.application.user.implement.UserReader;
import com.danthis.backend.application.user.implement.mapping.UserDancerManager;
import com.danthis.backend.application.user.implement.mapping.UserDancerReader;
import com.danthis.backend.application.user.implement.mapping.UserGenreManager;
import com.danthis.backend.application.user.implement.mapping.UserGenreReader;
import com.danthis.backend.application.user.request.UserUpdateServiceRequest;
import com.danthis.backend.application.user.response.UserInfoResponse;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserReader userReader;
  private final UserManager userManager;
  private final UserPreferenceMapper userPreferenceMapper;
  private final DancerReader dancerReader;
  private final UserDancerManager userDancerManager;
  private final UserDancerReader userDancerReader;
  private final UserGenreManager userGenreManager;
  private final UserGenreReader userGenreReader;

  @Transactional
  public void updateUserInfo(Long userId, UserUpdateServiceRequest request) {
    User user = userReader.readUserById(userId);

    user.updateNickname(request.getNickname());
    user.updateGender(request.getGender());
    user.updatePhoneNumber(request.getPhoneNumber());
    user.updateProfileImage(request.getProfileImage());

    Set<Genre> genres = userPreferenceMapper.mapToGenres(request.getPreferredGenres());
    Set<Dancer> dancers = userPreferenceMapper.mapToDancers(request.getPreferredDancers());

    userGenreManager.deleteByUser(user);
    userDancerManager.deleteByUser(user);

    Set<UserGenre> updatedGenres = UserGenre.createFromIds(user, genres);
    Set<UserDancer> updatedDancers = UserDancer.createFromIds(user, dancers);

    userGenreManager.saveAll(updatedGenres);
    userDancerManager.saveAll(updatedDancers);

    user.updatePreferredGenres(updatedGenres);
    user.updatePreferredDancers(updatedDancers);

    userManager.saveUser(user);
  }

  @Transactional
  public UserInfoResponse getUserInfo(Long userId) {
    User user = userReader.readUserById(userId);

    return UserInfoResponse.builder()
                           .nickname(user.getNickname())
                           .gender(user.getGender())
                           .email(user.getEmail())
                           .phoneNumber(user.getPhoneNumber())
                           .profileImage(user.getProfileImage())
                           .preferredGenres(userGenreReader.findGenreIdsByUser(user))
                           .preferredDancers(userDancerReader.findDancerIdsByUser(user))
                           .build();
  }

  @Transactional
  public boolean isEmailRegistered(String email) {
    return userReader.existsByEmail(email);
  }

  @Transactional
  public boolean isNicknameAvailable(String nickname) {
    return userReader.isNicknameAvailable(nickname);
  }

  @Transactional
  public void addFavoriteDancer(Long dancerId, Long userId) {
    User user = userReader.readUserById(userId);
    Dancer dancer = dancerReader.readDancerById(dancerId);
    UserDancer userDancer = userDancerManager.toUserDancer(user, dancer);

    userDancerManager.saveUserDancer(userDancer);
  }

  @Transactional
  public void removeFavoriteDancer(Long dancerId, Long userId) {
    User user = userReader.readUserById(userId);
    Dancer dancer = dancerReader.readDancerById(dancerId);
    UserDancer userDancer = userDancerReader.readUserDancerByUserAndDancer(user, dancer);

    userDancerManager.deleteUserDancer(userDancer);
  }
}
