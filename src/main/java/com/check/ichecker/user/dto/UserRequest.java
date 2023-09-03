package com.check.ichecker.user.dto;

import com.check.ichecker.user.Model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String userId;

    private String password;

    private String grade;

    private String name;
}