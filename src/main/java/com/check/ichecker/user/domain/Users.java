package com.check.ichecker.user.domain;

import com.check.ichecker.user.Model.Role;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
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

    public Users() {
        this.name = "";
        this.userId = "";
        this.password = "";
        this.grade = "";
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}