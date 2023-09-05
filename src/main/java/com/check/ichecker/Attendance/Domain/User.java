package com.check.ichecker.Attendance.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String grade;
    private String name;
    private String password;
    private String role;

    @Column(name = "user_id")
    private String userid;


}
