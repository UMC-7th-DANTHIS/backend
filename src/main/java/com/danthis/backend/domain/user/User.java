package com.danthis.backend.domain.user;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.mapping.userdancer.UserDancer;
import com.danthis.backend.domain.mapping.usergenre.UserGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

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

  @Column(nullable = false, length = 10)
  private String gender;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String phoneNumber;

  private String profileImage;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<UserGenre> userGenres;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<UserDancer> userDancers;
}
