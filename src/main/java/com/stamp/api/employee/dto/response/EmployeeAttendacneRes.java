package com.stamp.api.employee.dto.response;

import com.stamp.api.attendance.entity.AttendanceEnum;
import java.time.LocalDate;
import java.time.LocalTime;

public record EmployeeAttendacneRes(
    String id, // attendance Entity id
    AttendanceEnum attendanceEnum,
    LocalDate date,
    LocalTime time) {}
