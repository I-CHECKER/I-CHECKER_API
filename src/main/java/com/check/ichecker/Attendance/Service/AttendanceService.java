package com.check.ichecker.Attendance.Service;

import com.check.ichecker.Attendance.DTO.AttendanceDTO;
import com.check.ichecker.Attendance.DTO.AttendanceResponseDTO;
import com.check.ichecker.Attendance.Domain.Attendance;
import com.check.ichecker.Attendance.Domain.User;
import com.check.ichecker.Attendance.Repository.AttendanceRepository;
import com.check.ichecker.Attendance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    private AttendanceRepository attendanceRepository;
    private UserRepository userRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository){
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    public void addCheck(AttendanceDTO attendanceDTO){
        Attendance attendance = new Attendance();

        attendance.setDate(attendanceDTO.getDate());
        attendance.setTime(attendanceDTO.getTime());
        attendance.setUserid(attendanceDTO.getUserid());

        attendanceRepository.save(attendance);
    }

    public List<AttendanceResponseDTO> monthCheck(String date){
        List<Attendance> attendances = attendanceRepository.findAllByDateContainingOrderByDateAscUseridAsc(date);
        List<AttendanceResponseDTO> result = new ArrayList<>();
        for (Attendance attendance : attendances){
            AttendanceResponseDTO attendanceResponseDTO = new AttendanceResponseDTO();
            attendanceResponseDTO.setDate(attendance.getDate());
            attendanceResponseDTO.setTime(attendance.getTime());

            User user = userRepository.findByUserid(attendance.getUserid());

            attendanceResponseDTO.setName(user.getName());
            attendanceResponseDTO.setId(user.getId());

            result.add(attendanceResponseDTO);
        }
        return result;
    }
}
