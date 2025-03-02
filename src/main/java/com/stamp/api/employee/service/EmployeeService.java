package com.stamp.api.employee.service;

import com.stamp.api.attendance.dto.response.AttendanceRes;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

public interface EmployeeService {

  List<AttendanceRes> getAttendancesForMonth(LocalDate firstDate, UserDetails userDetails);

  List<AttendanceRes> getAttendancesForDay(LocalDate firstDate, UserDetails userDetails);
}
