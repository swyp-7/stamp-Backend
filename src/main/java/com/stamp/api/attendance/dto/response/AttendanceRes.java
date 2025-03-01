package com.stamp.api.attendance.dto.response;

import com.stamp.api.attendance.entity.AttendanceEnum;
import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRes(
    String id,
    String name,
    Long employeeId,
    AttendanceEnum attendanceEnum,
    LocalDate date,
    LocalTime time) {}
