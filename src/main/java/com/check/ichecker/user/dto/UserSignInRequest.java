package com.check.ichecker.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInRequest {

    private String userId;
    private String password;
}
