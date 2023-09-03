package com.check.ichecker.user.domain;

import com.check.ichecker.user.Model.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity
public class Users {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String password;

    private String grade;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    @Transient
    private boolean approved = false;

    @Builder
    public Users(String userId, String password, String grade, Role role, String name) {
        this.userId = userId;
        this.grade = grade;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}