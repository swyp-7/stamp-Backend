package com.stamp.api.employee.service;

import com.stamp.api.employee.dto.response.EmployeeAttendacneRes;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

public interface EmployeeService {

  List<EmployeeAttendacneRes> getAttendancesForMonth(LocalDate firstDate, UserDetails userDetails);

  List<EmployeeAttendacneRes> getAttendancesForDay(LocalDate firstDate, UserDetails userDetails);
}
