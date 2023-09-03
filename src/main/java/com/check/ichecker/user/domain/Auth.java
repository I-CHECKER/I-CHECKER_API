package com.check.ichecker.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity
public class Auth {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Builder
    public Auth(String refreshToken, Users users) {
        this.refreshToken = refreshToken;
        this.users = users;
    }
    public void refreshUpdate(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}