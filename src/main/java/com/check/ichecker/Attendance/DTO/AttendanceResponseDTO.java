package com.check.ichecker.Attendance.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceResponseDTO {
    private Long id;
    private String name;
    private String date;
    private String time;
}
