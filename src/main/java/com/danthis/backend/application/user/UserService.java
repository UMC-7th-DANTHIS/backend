package com.danthis.backend.application.user;

import com.danthis.backend.application.user.implement.*;
import com.danthis.backend.application.user.implement.mapping.UserDancerManager;
import com.danthis.backend.application.user.implement.mapping.UserDancerReader;
import com.danthis.backend.application.user.implement.mapping.UserGenreManager;
import com.danthis.backend.application.user.implement.mapping.UserGenreReader;
import com.danthis.backend.application.user.implement.mapping.WishListManager;
import com.danthis.backend.application.user.implement.mapping.WishListReader;
import com.danthis.backend.application.user.request.UserUpdateServiceRequest;
import com.danthis.backend.application.user.response.UserFavoriteResponse.FavoriteDancerListResponse;
import com.danthis.backend.application.user.response.UserFavoriteResponse.WishListResponse;
import com.danthis.backend.application.user.response.UserInfoResponse;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.genre.Genre;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import com.danthis.backend.domain.mapping.wishlist.WishList;
import com.danthis.backend.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserReader userReader;
  private final UserManager userManager;
  private final UserPreferenceMapper userPreferenceMapper;
  private final UserGenreManager userGenreManager;
  private final UserDancerManager userDancerManager;
  private final UserGenreReader userGenreReader;
  private final UserDancerReader userDancerReader;
  private final WishListManager wishListManager;
  private final WishListReader wishListReader;

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
  public FavoriteDancerListResponse getFavoriteDancers(Long userId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<UserDancer> dancers = userDancerReader.readDancersByUserId(userId, pageable);
    return FavoriteDancerListResponse.from(dancers);
  }

  @Transactional
  public WishListResponse getWishList(Long userId, Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<WishList> wishLists = wishListReader.readWishListByUserId(userId, pageable);
    return WishListResponse.from(wishLists);
  }
}
