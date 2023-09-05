package com.check.ichecker.Attendance.Controller;

import com.check.ichecker.Attendance.DTO.AttendanceDTO;
import com.check.ichecker.Attendance.DTO.AttendanceResponseDTO;
import com.check.ichecker.Attendance.Domain.Attendance;
import com.check.ichecker.Attendance.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService){
        this.attendanceService = attendanceService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCheck(@RequestBody AttendanceDTO attendanceDTO){
        attendanceService.addCheck(attendanceDTO);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/month/{date}")
    public ResponseEntity<List<AttendanceResponseDTO>> monthCheck(@PathVariable String date){
        return ResponseEntity.ok().body(attendanceService.monthCheck(date));
    }

}
