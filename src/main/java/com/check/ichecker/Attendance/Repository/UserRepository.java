package com.check.ichecker.Attendance.Repository;

import com.check.ichecker.Attendance.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserid(String userid);
}
