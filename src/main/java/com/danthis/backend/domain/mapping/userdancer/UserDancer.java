package com.danthis.backend.domain.mapping.userdancer;

import com.danthis.backend.domain.BaseEntity;
import com.danthis.backend.domain.dancer.Dancer;
import com.danthis.backend.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserDancer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dancer_id")
    private Dancer dancer;
}
