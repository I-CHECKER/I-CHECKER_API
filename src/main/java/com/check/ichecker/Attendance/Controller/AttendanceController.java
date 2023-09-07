package com.check.ichecker.Attendance.Controller;


import com.check.ichecker.Attendance.DTO.AttendanceDTO;
import com.check.ichecker.Attendance.DTO.AttendanceResponseDTO;
import com.check.ichecker.Attendance.Service.AttendanceService;
import com.check.ichecker.jwt.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/attendance")
public class AttendanceController {


    private final AttendanceService attendanceService;
    private final TokenUtils tokenUtils;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, TokenUtils tokenUtils) {
        this.attendanceService = attendanceService;
        this.tokenUtils = tokenUtils;
    }


    @PostMapping("/add")
    public ResponseEntity<String> addCheck(HttpServletRequest request,
                                           @RequestBody AttendanceDTO attendanceDTO){

        String userId = tokenUtils.getUserIdFromToken(request);

        if(userId.equals("Invalid token")){
            return ResponseEntity.badRequest().body("Invalid token");
        }

        attendanceService.addCheck(userId, attendanceDTO);

        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/month/{date}")
    public ResponseEntity<List<AttendanceResponseDTO>> monthCheck(@PathVariable String date){
        return ResponseEntity.ok().body(attendanceService.monthCheck(date));
    }

}
