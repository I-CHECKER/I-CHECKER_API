package com.check.ichecker.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSignInRequest {

    private String userId;
    private String password;
}
