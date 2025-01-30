package com.danthis.backend.domain.user;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.classreview.ClassReview;
import com.danthis.backend.domain.communitycomment.CommunityComment;
import com.danthis.backend.domain.communitypost.CommunityPost;
import com.danthis.backend.domain.mapping.danceclassbooking.DanceClassBooking;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import com.danthis.backend.domain.mapping.wishlist.WishList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String nickname;

  @Column(length = 10)
  private String gender;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(unique = true)
  private String phoneNumber;

  private String profileImage;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<ClassReview> classReviews;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<CommunityPost> communityPosts;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<CommunityComment> communityComments;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<DanceClassBooking> danceClassBookings;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<UserDancer> userDancers;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<UserGenre> userGenres;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<WishList> wishLists;

  public void updateNickname(String nickname) {
    this.nickname = nickname;
  }

  public void updateGender(String gender) {
    this.gender = gender;
  }

  public void updatePhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void updateProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

  public void updatePreferredGenres(Set<UserGenre> genres) {
    this.userGenres.clear();
    this.userGenres.addAll(genres);
  }

  public void updatePreferredDancers(Set<UserDancer> dancers) {
    this.userDancers.clear();
    this.userDancers.addAll(dancers);
  }
}
