package com.stamp.api.attendance.dto.requeset;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stamp.api.attendance.entity.AttendanceEnum;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceUpdateReq(
    Long employeeId,
    AttendanceEnum attendanceEnum,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
    @JsonFormat(pattern = "HH:mm:ss") LocalTime time) {}
