package com.check.ichecker.Attendance.Repository;

import com.check.ichecker.Attendance.Domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByDateContainingOrderByDateAscUseridAsc(String date);
}
