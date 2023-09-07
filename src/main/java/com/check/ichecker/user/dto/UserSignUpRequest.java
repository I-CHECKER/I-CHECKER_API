package com.check.ichecker.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest {
    private String userId;

    private String password;

    private String grade;

    private String name;
}